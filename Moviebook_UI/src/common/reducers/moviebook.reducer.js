import {moviebookConstants} from "../constants";

const initialState = {
    movieList: []
};

export function moviebook (state = initialState, action) {
    switch (action.type) {
        case moviebookConstants.LIST_MOVIES:
            return {
                ...state,
                movieList: action.data
            };
        default:
            return state
    }
}