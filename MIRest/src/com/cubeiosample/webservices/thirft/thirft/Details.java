/**
 * Autogenerated by Thrift Compiler (0.12.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.cubeiosample.webservices.thirft.thirft;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
/**
 * * {
 *     "display_actors": [
 *       "NOLTE,JAYNE",
 *       "WILLIS,HUMPHREY",
 *       "MARX,ELVIS"
 *     ],
 *     "film_id": 23,
 *     "title": "ANACONDA CONFESSIONS",
 *     "film_counts": [
 *       "22",
 *       "26",
 *       "34",
 *       "19",
 *       "26"
 *     ],
 *     "timestamp": 572790039541041,
 *     "book_info": {
 *       "reviews": [
 *         {
 *           "reviewer": "Reviewer1",
 *           "text": "An extremely entertaining play by Shakespeare. The slapstick humour is refreshing!"
 *         },
 *         {
 *           "reviewer": "Reviewer2",
 *           "text": "Absolutely fun and entertaining. The play lacks thematic depth when compared to other plays by Shakespeare."
 *         }
 *       ],
 *       "ratings": {
 *         "Reviewer2": 4,
 *         "Reviewer1": 5
 *       },
 *       "details": {
 *         "pages": 200,
 *         "year": 1595,
 *         "author": "William Shakespeare",
 *         "ISBN-13": "123-1234567890",
 *         "publisher": "PublisherA",
 *         "ISBN-10": "1234567890",
 *         "language": "English",
 *         "id": 23,
 *         "type": "paperback"
 *       }
 *     }
 *   }
 * *
 */
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.12.0)", date = "2019-10-02")
public class Details implements org.apache.thrift.TBase<Details, Details._Fields>, java.io.Serializable, Cloneable, Comparable<Details> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Details");

  private static final org.apache.thrift.protocol.TField PAGES_FIELD_DESC = new org.apache.thrift.protocol.TField("pages", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField YEAR_FIELD_DESC = new org.apache.thrift.protocol.TField("year", org.apache.thrift.protocol.TType.I16, (short)2);
  private static final org.apache.thrift.protocol.TField AUTHOR_FIELD_DESC = new org.apache.thrift.protocol.TField("author", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField ISBN_13_FIELD_DESC = new org.apache.thrift.protocol.TField("isbn_13", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField PUBLISHER_FIELD_DESC = new org.apache.thrift.protocol.TField("publisher", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField ISBN_10_FIELD_DESC = new org.apache.thrift.protocol.TField("isbn_10", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField LANGUAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("language", org.apache.thrift.protocol.TType.STRING, (short)7);
  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.STRING, (short)8);
  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I32, (short)9);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new DetailsStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new DetailsTupleSchemeFactory();

  public int pages; // required
  public short year; // required
  public @org.apache.thrift.annotation.Nullable java.lang.String author; // required
  public @org.apache.thrift.annotation.Nullable java.lang.String isbn_13; // required
  public @org.apache.thrift.annotation.Nullable java.lang.String publisher; // required
  public @org.apache.thrift.annotation.Nullable java.lang.String isbn_10; // required
  public @org.apache.thrift.annotation.Nullable java.lang.String language; // required
  public @org.apache.thrift.annotation.Nullable java.lang.String type; // required
  public int id; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PAGES((short)1, "pages"),
    YEAR((short)2, "year"),
    AUTHOR((short)3, "author"),
    ISBN_13((short)4, "isbn_13"),
    PUBLISHER((short)5, "publisher"),
    ISBN_10((short)6, "isbn_10"),
    LANGUAGE((short)7, "language"),
    TYPE((short)8, "type"),
    ID((short)9, "id");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // PAGES
          return PAGES;
        case 2: // YEAR
          return YEAR;
        case 3: // AUTHOR
          return AUTHOR;
        case 4: // ISBN_13
          return ISBN_13;
        case 5: // PUBLISHER
          return PUBLISHER;
        case 6: // ISBN_10
          return ISBN_10;
        case 7: // LANGUAGE
          return LANGUAGE;
        case 8: // TYPE
          return TYPE;
        case 9: // ID
          return ID;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __PAGES_ISSET_ID = 0;
  private static final int __YEAR_ISSET_ID = 1;
  private static final int __ID_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PAGES, new org.apache.thrift.meta_data.FieldMetaData("pages", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.YEAR, new org.apache.thrift.meta_data.FieldMetaData("year", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16)));
    tmpMap.put(_Fields.AUTHOR, new org.apache.thrift.meta_data.FieldMetaData("author", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ISBN_13, new org.apache.thrift.meta_data.FieldMetaData("isbn_13", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PUBLISHER, new org.apache.thrift.meta_data.FieldMetaData("publisher", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ISBN_10, new org.apache.thrift.meta_data.FieldMetaData("isbn_10", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.LANGUAGE, new org.apache.thrift.meta_data.FieldMetaData("language", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Details.class, metaDataMap);
  }

  public Details() {
  }

  public Details(
    int pages,
    short year,
    java.lang.String author,
    java.lang.String isbn_13,
    java.lang.String publisher,
    java.lang.String isbn_10,
    java.lang.String language,
    java.lang.String type,
    int id)
  {
    this();
    this.pages = pages;
    setPagesIsSet(true);
    this.year = year;
    setYearIsSet(true);
    this.author = author;
    this.isbn_13 = isbn_13;
    this.publisher = publisher;
    this.isbn_10 = isbn_10;
    this.language = language;
    this.type = type;
    this.id = id;
    setIdIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Details(Details other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pages = other.pages;
    this.year = other.year;
    if (other.isSetAuthor()) {
      this.author = other.author;
    }
    if (other.isSetIsbn_13()) {
      this.isbn_13 = other.isbn_13;
    }
    if (other.isSetPublisher()) {
      this.publisher = other.publisher;
    }
    if (other.isSetIsbn_10()) {
      this.isbn_10 = other.isbn_10;
    }
    if (other.isSetLanguage()) {
      this.language = other.language;
    }
    if (other.isSetType()) {
      this.type = other.type;
    }
    this.id = other.id;
  }

  public Details deepCopy() {
    return new Details(this);
  }

  @Override
  public void clear() {
    setPagesIsSet(false);
    this.pages = 0;
    setYearIsSet(false);
    this.year = 0;
    this.author = null;
    this.isbn_13 = null;
    this.publisher = null;
    this.isbn_10 = null;
    this.language = null;
    this.type = null;
    setIdIsSet(false);
    this.id = 0;
  }

  public int getPages() {
    return this.pages;
  }

  public Details setPages(int pages) {
    this.pages = pages;
    setPagesIsSet(true);
    return this;
  }

  public void unsetPages() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __PAGES_ISSET_ID);
  }

  /** Returns true if field pages is set (has been assigned a value) and false otherwise */
  public boolean isSetPages() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __PAGES_ISSET_ID);
  }

  public void setPagesIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __PAGES_ISSET_ID, value);
  }

  public short getYear() {
    return this.year;
  }

  public Details setYear(short year) {
    this.year = year;
    setYearIsSet(true);
    return this;
  }

  public void unsetYear() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __YEAR_ISSET_ID);
  }

  /** Returns true if field year is set (has been assigned a value) and false otherwise */
  public boolean isSetYear() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __YEAR_ISSET_ID);
  }

  public void setYearIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __YEAR_ISSET_ID, value);
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getAuthor() {
    return this.author;
  }

  public Details setAuthor(@org.apache.thrift.annotation.Nullable java.lang.String author) {
    this.author = author;
    return this;
  }

  public void unsetAuthor() {
    this.author = null;
  }

  /** Returns true if field author is set (has been assigned a value) and false otherwise */
  public boolean isSetAuthor() {
    return this.author != null;
  }

  public void setAuthorIsSet(boolean value) {
    if (!value) {
      this.author = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getIsbn_13() {
    return this.isbn_13;
  }

  public Details setIsbn_13(@org.apache.thrift.annotation.Nullable java.lang.String isbn_13) {
    this.isbn_13 = isbn_13;
    return this;
  }

  public void unsetIsbn_13() {
    this.isbn_13 = null;
  }

  /** Returns true if field isbn_13 is set (has been assigned a value) and false otherwise */
  public boolean isSetIsbn_13() {
    return this.isbn_13 != null;
  }

  public void setIsbn_13IsSet(boolean value) {
    if (!value) {
      this.isbn_13 = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getPublisher() {
    return this.publisher;
  }

  public Details setPublisher(@org.apache.thrift.annotation.Nullable java.lang.String publisher) {
    this.publisher = publisher;
    return this;
  }

  public void unsetPublisher() {
    this.publisher = null;
  }

  /** Returns true if field publisher is set (has been assigned a value) and false otherwise */
  public boolean isSetPublisher() {
    return this.publisher != null;
  }

  public void setPublisherIsSet(boolean value) {
    if (!value) {
      this.publisher = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getIsbn_10() {
    return this.isbn_10;
  }

  public Details setIsbn_10(@org.apache.thrift.annotation.Nullable java.lang.String isbn_10) {
    this.isbn_10 = isbn_10;
    return this;
  }

  public void unsetIsbn_10() {
    this.isbn_10 = null;
  }

  /** Returns true if field isbn_10 is set (has been assigned a value) and false otherwise */
  public boolean isSetIsbn_10() {
    return this.isbn_10 != null;
  }

  public void setIsbn_10IsSet(boolean value) {
    if (!value) {
      this.isbn_10 = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getLanguage() {
    return this.language;
  }

  public Details setLanguage(@org.apache.thrift.annotation.Nullable java.lang.String language) {
    this.language = language;
    return this;
  }

  public void unsetLanguage() {
    this.language = null;
  }

  /** Returns true if field language is set (has been assigned a value) and false otherwise */
  public boolean isSetLanguage() {
    return this.language != null;
  }

  public void setLanguageIsSet(boolean value) {
    if (!value) {
      this.language = null;
    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getType() {
    return this.type;
  }

  public Details setType(@org.apache.thrift.annotation.Nullable java.lang.String type) {
    this.type = type;
    return this;
  }

  public void unsetType() {
    this.type = null;
  }

  /** Returns true if field type is set (has been assigned a value) and false otherwise */
  public boolean isSetType() {
    return this.type != null;
  }

  public void setTypeIsSet(boolean value) {
    if (!value) {
      this.type = null;
    }
  }

  public int getId() {
    return this.id;
  }

  public Details setId(int id) {
    this.id = id;
    setIdIsSet(true);
    return this;
  }

  public void unsetId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ID_ISSET_ID);
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ID_ISSET_ID);
  }

  public void setIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ID_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case PAGES:
      if (value == null) {
        unsetPages();
      } else {
        setPages((java.lang.Integer)value);
      }
      break;

    case YEAR:
      if (value == null) {
        unsetYear();
      } else {
        setYear((java.lang.Short)value);
      }
      break;

    case AUTHOR:
      if (value == null) {
        unsetAuthor();
      } else {
        setAuthor((java.lang.String)value);
      }
      break;

    case ISBN_13:
      if (value == null) {
        unsetIsbn_13();
      } else {
        setIsbn_13((java.lang.String)value);
      }
      break;

    case PUBLISHER:
      if (value == null) {
        unsetPublisher();
      } else {
        setPublisher((java.lang.String)value);
      }
      break;

    case ISBN_10:
      if (value == null) {
        unsetIsbn_10();
      } else {
        setIsbn_10((java.lang.String)value);
      }
      break;

    case LANGUAGE:
      if (value == null) {
        unsetLanguage();
      } else {
        setLanguage((java.lang.String)value);
      }
      break;

    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((java.lang.String)value);
      }
      break;

    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((java.lang.Integer)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case PAGES:
      return getPages();

    case YEAR:
      return getYear();

    case AUTHOR:
      return getAuthor();

    case ISBN_13:
      return getIsbn_13();

    case PUBLISHER:
      return getPublisher();

    case ISBN_10:
      return getIsbn_10();

    case LANGUAGE:
      return getLanguage();

    case TYPE:
      return getType();

    case ID:
      return getId();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case PAGES:
      return isSetPages();
    case YEAR:
      return isSetYear();
    case AUTHOR:
      return isSetAuthor();
    case ISBN_13:
      return isSetIsbn_13();
    case PUBLISHER:
      return isSetPublisher();
    case ISBN_10:
      return isSetIsbn_10();
    case LANGUAGE:
      return isSetLanguage();
    case TYPE:
      return isSetType();
    case ID:
      return isSetId();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof Details)
      return this.equals((Details)that);
    return false;
  }

  public boolean equals(Details that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_pages = true;
    boolean that_present_pages = true;
    if (this_present_pages || that_present_pages) {
      if (!(this_present_pages && that_present_pages))
        return false;
      if (this.pages != that.pages)
        return false;
    }

    boolean this_present_year = true;
    boolean that_present_year = true;
    if (this_present_year || that_present_year) {
      if (!(this_present_year && that_present_year))
        return false;
      if (this.year != that.year)
        return false;
    }

    boolean this_present_author = true && this.isSetAuthor();
    boolean that_present_author = true && that.isSetAuthor();
    if (this_present_author || that_present_author) {
      if (!(this_present_author && that_present_author))
        return false;
      if (!this.author.equals(that.author))
        return false;
    }

    boolean this_present_isbn_13 = true && this.isSetIsbn_13();
    boolean that_present_isbn_13 = true && that.isSetIsbn_13();
    if (this_present_isbn_13 || that_present_isbn_13) {
      if (!(this_present_isbn_13 && that_present_isbn_13))
        return false;
      if (!this.isbn_13.equals(that.isbn_13))
        return false;
    }

    boolean this_present_publisher = true && this.isSetPublisher();
    boolean that_present_publisher = true && that.isSetPublisher();
    if (this_present_publisher || that_present_publisher) {
      if (!(this_present_publisher && that_present_publisher))
        return false;
      if (!this.publisher.equals(that.publisher))
        return false;
    }

    boolean this_present_isbn_10 = true && this.isSetIsbn_10();
    boolean that_present_isbn_10 = true && that.isSetIsbn_10();
    if (this_present_isbn_10 || that_present_isbn_10) {
      if (!(this_present_isbn_10 && that_present_isbn_10))
        return false;
      if (!this.isbn_10.equals(that.isbn_10))
        return false;
    }

    boolean this_present_language = true && this.isSetLanguage();
    boolean that_present_language = true && that.isSetLanguage();
    if (this_present_language || that_present_language) {
      if (!(this_present_language && that_present_language))
        return false;
      if (!this.language.equals(that.language))
        return false;
    }

    boolean this_present_type = true && this.isSetType();
    boolean that_present_type = true && that.isSetType();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
        return false;
    }

    boolean this_present_id = true;
    boolean that_present_id = true;
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + pages;

    hashCode = hashCode * 8191 + year;

    hashCode = hashCode * 8191 + ((isSetAuthor()) ? 131071 : 524287);
    if (isSetAuthor())
      hashCode = hashCode * 8191 + author.hashCode();

    hashCode = hashCode * 8191 + ((isSetIsbn_13()) ? 131071 : 524287);
    if (isSetIsbn_13())
      hashCode = hashCode * 8191 + isbn_13.hashCode();

    hashCode = hashCode * 8191 + ((isSetPublisher()) ? 131071 : 524287);
    if (isSetPublisher())
      hashCode = hashCode * 8191 + publisher.hashCode();

    hashCode = hashCode * 8191 + ((isSetIsbn_10()) ? 131071 : 524287);
    if (isSetIsbn_10())
      hashCode = hashCode * 8191 + isbn_10.hashCode();

    hashCode = hashCode * 8191 + ((isSetLanguage()) ? 131071 : 524287);
    if (isSetLanguage())
      hashCode = hashCode * 8191 + language.hashCode();

    hashCode = hashCode * 8191 + ((isSetType()) ? 131071 : 524287);
    if (isSetType())
      hashCode = hashCode * 8191 + type.hashCode();

    hashCode = hashCode * 8191 + id;

    return hashCode;
  }

  @Override
  public int compareTo(Details other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetPages()).compareTo(other.isSetPages());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPages()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pages, other.pages);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetYear()).compareTo(other.isSetYear());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetYear()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.year, other.year);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetAuthor()).compareTo(other.isSetAuthor());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAuthor()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.author, other.author);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetIsbn_13()).compareTo(other.isSetIsbn_13());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIsbn_13()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.isbn_13, other.isbn_13);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPublisher()).compareTo(other.isSetPublisher());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPublisher()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.publisher, other.publisher);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetIsbn_10()).compareTo(other.isSetIsbn_10());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIsbn_10()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.isbn_10, other.isbn_10);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetLanguage()).compareTo(other.isSetLanguage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLanguage()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.language, other.language);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetType()).compareTo(other.isSetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.type, other.type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  @org.apache.thrift.annotation.Nullable
  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("Details(");
    boolean first = true;

    sb.append("pages:");
    sb.append(this.pages);
    first = false;
    if (!first) sb.append(", ");
    sb.append("year:");
    sb.append(this.year);
    first = false;
    if (!first) sb.append(", ");
    sb.append("author:");
    if (this.author == null) {
      sb.append("null");
    } else {
      sb.append(this.author);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("isbn_13:");
    if (this.isbn_13 == null) {
      sb.append("null");
    } else {
      sb.append(this.isbn_13);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("publisher:");
    if (this.publisher == null) {
      sb.append("null");
    } else {
      sb.append(this.publisher);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("isbn_10:");
    if (this.isbn_10 == null) {
      sb.append("null");
    } else {
      sb.append(this.isbn_10);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("language:");
    if (this.language == null) {
      sb.append("null");
    } else {
      sb.append(this.language);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("type:");
    if (this.type == null) {
      sb.append("null");
    } else {
      sb.append(this.type);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("id:");
    sb.append(this.id);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class DetailsStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public DetailsStandardScheme getScheme() {
      return new DetailsStandardScheme();
    }
  }

  private static class DetailsStandardScheme extends org.apache.thrift.scheme.StandardScheme<Details> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Details struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PAGES
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.pages = iprot.readI32();
              struct.setPagesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // YEAR
            if (schemeField.type == org.apache.thrift.protocol.TType.I16) {
              struct.year = iprot.readI16();
              struct.setYearIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // AUTHOR
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.author = iprot.readString();
              struct.setAuthorIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // ISBN_13
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.isbn_13 = iprot.readString();
              struct.setIsbn_13IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PUBLISHER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.publisher = iprot.readString();
              struct.setPublisherIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // ISBN_10
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.isbn_10 = iprot.readString();
              struct.setIsbn_10IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // LANGUAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.language = iprot.readString();
              struct.setLanguageIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.type = iprot.readString();
              struct.setTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 9: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.id = iprot.readI32();
              struct.setIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Details struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PAGES_FIELD_DESC);
      oprot.writeI32(struct.pages);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(YEAR_FIELD_DESC);
      oprot.writeI16(struct.year);
      oprot.writeFieldEnd();
      if (struct.author != null) {
        oprot.writeFieldBegin(AUTHOR_FIELD_DESC);
        oprot.writeString(struct.author);
        oprot.writeFieldEnd();
      }
      if (struct.isbn_13 != null) {
        oprot.writeFieldBegin(ISBN_13_FIELD_DESC);
        oprot.writeString(struct.isbn_13);
        oprot.writeFieldEnd();
      }
      if (struct.publisher != null) {
        oprot.writeFieldBegin(PUBLISHER_FIELD_DESC);
        oprot.writeString(struct.publisher);
        oprot.writeFieldEnd();
      }
      if (struct.isbn_10 != null) {
        oprot.writeFieldBegin(ISBN_10_FIELD_DESC);
        oprot.writeString(struct.isbn_10);
        oprot.writeFieldEnd();
      }
      if (struct.language != null) {
        oprot.writeFieldBegin(LANGUAGE_FIELD_DESC);
        oprot.writeString(struct.language);
        oprot.writeFieldEnd();
      }
      if (struct.type != null) {
        oprot.writeFieldBegin(TYPE_FIELD_DESC);
        oprot.writeString(struct.type);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(ID_FIELD_DESC);
      oprot.writeI32(struct.id);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class DetailsTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public DetailsTupleScheme getScheme() {
      return new DetailsTupleScheme();
    }
  }

  private static class DetailsTupleScheme extends org.apache.thrift.scheme.TupleScheme<Details> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Details struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetPages()) {
        optionals.set(0);
      }
      if (struct.isSetYear()) {
        optionals.set(1);
      }
      if (struct.isSetAuthor()) {
        optionals.set(2);
      }
      if (struct.isSetIsbn_13()) {
        optionals.set(3);
      }
      if (struct.isSetPublisher()) {
        optionals.set(4);
      }
      if (struct.isSetIsbn_10()) {
        optionals.set(5);
      }
      if (struct.isSetLanguage()) {
        optionals.set(6);
      }
      if (struct.isSetType()) {
        optionals.set(7);
      }
      if (struct.isSetId()) {
        optionals.set(8);
      }
      oprot.writeBitSet(optionals, 9);
      if (struct.isSetPages()) {
        oprot.writeI32(struct.pages);
      }
      if (struct.isSetYear()) {
        oprot.writeI16(struct.year);
      }
      if (struct.isSetAuthor()) {
        oprot.writeString(struct.author);
      }
      if (struct.isSetIsbn_13()) {
        oprot.writeString(struct.isbn_13);
      }
      if (struct.isSetPublisher()) {
        oprot.writeString(struct.publisher);
      }
      if (struct.isSetIsbn_10()) {
        oprot.writeString(struct.isbn_10);
      }
      if (struct.isSetLanguage()) {
        oprot.writeString(struct.language);
      }
      if (struct.isSetType()) {
        oprot.writeString(struct.type);
      }
      if (struct.isSetId()) {
        oprot.writeI32(struct.id);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Details struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(9);
      if (incoming.get(0)) {
        struct.pages = iprot.readI32();
        struct.setPagesIsSet(true);
      }
      if (incoming.get(1)) {
        struct.year = iprot.readI16();
        struct.setYearIsSet(true);
      }
      if (incoming.get(2)) {
        struct.author = iprot.readString();
        struct.setAuthorIsSet(true);
      }
      if (incoming.get(3)) {
        struct.isbn_13 = iprot.readString();
        struct.setIsbn_13IsSet(true);
      }
      if (incoming.get(4)) {
        struct.publisher = iprot.readString();
        struct.setPublisherIsSet(true);
      }
      if (incoming.get(5)) {
        struct.isbn_10 = iprot.readString();
        struct.setIsbn_10IsSet(true);
      }
      if (incoming.get(6)) {
        struct.language = iprot.readString();
        struct.setLanguageIsSet(true);
      }
      if (incoming.get(7)) {
        struct.type = iprot.readString();
        struct.setTypeIsSet(true);
      }
      if (incoming.get(8)) {
        struct.id = iprot.readI32();
        struct.setIdIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

