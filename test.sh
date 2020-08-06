#!/usr/bin/env bash

update_tag() {
RESPONSE="$(curl -X POST \
          $CUBE_ENDPOINT/api/cs/setCurrentAgentConfigTag \
          -H 'Content-Type: application/json' \
          -H 'cache-control: no-cache' \
          -H "Authorization: Bearer $AUTH_TOKEN" \
          -d '{
              "customerId":"CubeCorp",
              "app":"'$APP'",
              "instanceId":"'$INSTANCE_ID'",
              "service":"'$SERVICE'",
              "tag":"'$1'"
          }' )"
echo $RESPONSE
}

record() {
  RECORDING_ID=$(curl -X POST $CUBE_ENDPOINT/api/cs/start/CubeCorp/$APP/$INSTANCE_ID/default \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -H "Authorization: Bearer $AUTH_TOKEN" \
  -H 'cache-control: no-cache' \
  -d "name=jersy2x-$DRONE_BUILD_NUMBER&userId=CubeCorp&label=$(date +%s)" | jq .id | tr -d '"')
  echo $RECORDING_ID
}

generate_traffic() {
  for ((i=1;i<=$1;i++)); do
    curl -f -X GET "http://jersy2x.dev.cubecorp.io:8080/minfo/listmovies?filmName=BEVERLY%20OUTLAW"

    curl -X POST -H 'Content-Type:application/x-www-form-urlencoded' \
      -d 'filmName=ACADEMY DINOSAUR' \
      http://jersy2x.dev.cubecorp.io:8080/minfo/listmoviesbyformparams

	curl -f -X GET "http://jersy2x.dev.cubecorp.io:8080/minfo/liststores?filmId=1"

    RESPONSE=$(curl --request POST 'http://jersy2x.dev.cubecorp.io:8080/minfo/rentmovie/' --header 'Content-Type: application/json' --data-raw '{
	    "filmId": 1,
	    "storeId": 1,
	    "duration": 2,
	    "customerId": 200,
	    "staffId": 1
    }')
    echo $RESPONSE
    INVENTORY_ID=$(echo $RESPONSE | jq -r ".inventory_id")
    echo $INVENTORY_ID
    RENT=$(echo $RESPONSE | jq -r ".rent")
    echo $RENT

    curl --request POST 'http://jersy2x.dev.cubecorp.io:8080/minfo/returnmovie/' --header 'Content-Type: application/json' --data-raw '{
    	    "userId": 200,
    	    "staffId": 1,
    	    "rent":'"$RENT"',
    	    "inventoryId":'"$INVENTORY_ID"'
        }'


    curl -f -X GET "http://jersy2x.dev.cubecorp.io:8080/minfo/listmovies?filmName=ALABAMA%20DEVIL"

	curl -f -X GET "http://jersy2x.dev.cubecorp.io:8080/minfo/liststores?filmId=9"

    curl --request POST 'http://jersy2x.dev.cubecorp.io:8080/minfo/rentmovie/' --header 'Content-Type: application/json' --data-raw '{
	    "filmId": 9,
	    "storeId": 2,
	    "duration": 2,
	    "customerId": 200,
	    "staffId": 1
    }'

   curl -X POST "http://jersy2x.dev.cubecorp.io:8080/minfo/nocontent"

  done
}

stop_recording() {
  curl -X POST $CUBE_ENDPOINT/api/cs/stop/$RECORDING_ID \
  -H "Authorization: Bearer $AUTH_TOKEN"
}
replay() {
  BODY="endPoint=$REPLAY_ENDPOINT&instanceId=$INSTANCE_ID&templateSetVer=$TEMPLATE&userId=$USER_ID&service=$SERVICE"
  REPLAY_ID=$(curl -X POST \
		$CUBE_ENDPOINT/api/rs/start/$RECORDING_ID \
		-H 'Content-Type: application/x-www-form-urlencoded' \
		-H "Authorization: Bearer $AUTH_TOKEN" \
		-H 'cache-control: no-cache' \
		-d $BODY | jq .replayId | tr -d '"')
  COUNT=0
  while [ "$STATUS" != "Completed" ] && [ "$STATUS" != "Error" ] && [ "$COUNT" != "30" ]; do
    STATUS=$(curl -X GET $CUBE_ENDPOINT/api/rs/status/$REPLAY_ID -H "Authorization: Bearer $AUTH_TOKEN" | jq .status | tr -d '"')
    sleep 10
    COUNT=$((COUNT+1))
  done
}
analyze() {
  ANALYZE=$(curl -X POST $CUBE_ENDPOINT/api/as/analyze/$REPLAY_ID -H 'Content-Type: application/x-www-form-urlencoded' -H "Authorization: Bearer $AUTH_TOKEN" -H 'cache-control: no-cache')
  REQCOUNT=$(echo $ANALYZE | jq .reqCnt )
  RESPNOTMATCHED=$(echo $ANALYZE | jq .respNotMatched )

  #Display replay ID
  echo "Replay ID:" $REPLAY_ID
  #Exit with non-zero exit code if reqstnotmatched and respnotmatchted are have nono-zero value
  if [ "$RESPNOTMATCHED" = "0" ] && [ "$REQCOUNT" != "0" ]; then
    TEST_STATUS="test passed"
    EXIT_CODE=0
  else
    TEST_STATUS="test failed"
    EXIT_CODE=1
  fi
}
main() {
  set -x
  if [[ ! -f /usr/local/bin/jq ]]
  then
	  apk add jq
  fi
  CUBE_ENDPOINT=https://demo.dev.cubecorp.io
  TEMPLATE=DEFAULT
  USER_ID=CubeCorp
  REPLAY_ENDPOINT=http://jersy2x.dev.cubecorp.io
  APP=Jersy2xApp
  INSTANCE_ID=prod
  SERVICE=mirest
  AUTH_TOKEN="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNZXNoREFnZW50VXNlckBtZXNoZHluYW1pY3MuaW8iLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwidHlwZSI6InBhdCIsImN1c3RvbWVyX2lkIjoxLCJpYXQiOjE1ODI4ODE2MjgsImV4cCI6MTg5ODI0MTYyOH0.P4DAjXyODV8cFPgObaULjAMPg-7xSbUsVJ8Ohp7xTQI"
  update_tag MoviebookRecord
  sleep 20
  record
  generate_traffic 1
  sleep 20
  stop_recording
  sleep 20
  update_tag MoviebookReplay
  sleep 20
  replay
  analyze
  echo "Replay ID:" $REPLAY_ID
  	echo $TEST_STATUS
  exit $EXIT_CODE
}
main "$@"
