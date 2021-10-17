// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: addressbookV2.proto

package org.noahsark.tutorial.protos;

/**
 * <pre>
 * Our address book file is just one of these.
 * </pre>
 *
 * Protobuf type {@code tutorial.AddressBook}
 */
public final class AddressBook extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:tutorial.AddressBook)
    AddressBookOrBuilder {
private static final long serialVersionUID = 0L;
  // Use AddressBook.newBuilder() to construct.
  private AddressBook(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private AddressBook() {
    peoples_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new AddressBook();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private AddressBook(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              peoples_ = new java.util.ArrayList<org.noahsark.tutorial.protos.Person>();
              mutable_bitField0_ |= 0x00000001;
            }
            peoples_.add(
                input.readMessage(org.noahsark.tutorial.protos.Person.parser(), extensionRegistry));
            break;
          }
          case 18: {
            if (!((mutable_bitField0_ & 0x00000002) != 0)) {
              peopleMap_ = com.google.protobuf.MapField.newMapField(
                  PeopleMapDefaultEntryHolder.defaultEntry);
              mutable_bitField0_ |= 0x00000002;
            }
            com.google.protobuf.MapEntry<java.lang.String, org.noahsark.tutorial.protos.Person>
            peopleMap__ = input.readMessage(
                PeopleMapDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
            peopleMap_.getMutableMap().put(
                peopleMap__.getKey(), peopleMap__.getValue());
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        peoples_ = java.util.Collections.unmodifiableList(peoples_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.noahsark.tutorial.protos.AddressBookProtos.internal_static_tutorial_AddressBook_descriptor;
  }

  @SuppressWarnings({"rawtypes"})
  @java.lang.Override
  protected com.google.protobuf.MapField internalGetMapField(
      int number) {
    switch (number) {
      case 2:
        return internalGetPeopleMap();
      default:
        throw new RuntimeException(
            "Invalid map field number: " + number);
    }
  }
  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.noahsark.tutorial.protos.AddressBookProtos.internal_static_tutorial_AddressBook_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.noahsark.tutorial.protos.AddressBook.class, org.noahsark.tutorial.protos.AddressBook.Builder.class);
  }

  public static final int PEOPLES_FIELD_NUMBER = 1;
  private java.util.List<org.noahsark.tutorial.protos.Person> peoples_;
  /**
   * <code>repeated .tutorial.Person peoples = 1;</code>
   */
  @java.lang.Override
  public java.util.List<org.noahsark.tutorial.protos.Person> getPeoplesList() {
    return peoples_;
  }
  /**
   * <code>repeated .tutorial.Person peoples = 1;</code>
   */
  @java.lang.Override
  public java.util.List<? extends org.noahsark.tutorial.protos.PersonOrBuilder> 
      getPeoplesOrBuilderList() {
    return peoples_;
  }
  /**
   * <code>repeated .tutorial.Person peoples = 1;</code>
   */
  @java.lang.Override
  public int getPeoplesCount() {
    return peoples_.size();
  }
  /**
   * <code>repeated .tutorial.Person peoples = 1;</code>
   */
  @java.lang.Override
  public org.noahsark.tutorial.protos.Person getPeoples(int index) {
    return peoples_.get(index);
  }
  /**
   * <code>repeated .tutorial.Person peoples = 1;</code>
   */
  @java.lang.Override
  public org.noahsark.tutorial.protos.PersonOrBuilder getPeoplesOrBuilder(
      int index) {
    return peoples_.get(index);
  }

  public static final int PEOPLE_MAP_FIELD_NUMBER = 2;
  private static final class PeopleMapDefaultEntryHolder {
    static final com.google.protobuf.MapEntry<
        java.lang.String, org.noahsark.tutorial.protos.Person> defaultEntry =
            com.google.protobuf.MapEntry
            .<java.lang.String, org.noahsark.tutorial.protos.Person>newDefaultInstance(
                org.noahsark.tutorial.protos.AddressBookProtos.internal_static_tutorial_AddressBook_PeopleMapEntry_descriptor, 
                com.google.protobuf.WireFormat.FieldType.STRING,
                "",
                com.google.protobuf.WireFormat.FieldType.MESSAGE,
                org.noahsark.tutorial.protos.Person.getDefaultInstance());
  }
  private com.google.protobuf.MapField<
      java.lang.String, org.noahsark.tutorial.protos.Person> peopleMap_;
  private com.google.protobuf.MapField<java.lang.String, org.noahsark.tutorial.protos.Person>
  internalGetPeopleMap() {
    if (peopleMap_ == null) {
      return com.google.protobuf.MapField.emptyMapField(
          PeopleMapDefaultEntryHolder.defaultEntry);
    }
    return peopleMap_;
  }

  public int getPeopleMapCount() {
    return internalGetPeopleMap().getMap().size();
  }
  /**
   * <code>map&lt;string, .tutorial.Person&gt; people_map = 2;</code>
   */

  @java.lang.Override
  public boolean containsPeopleMap(
      java.lang.String key) {
    if (key == null) { throw new java.lang.NullPointerException(); }
    return internalGetPeopleMap().getMap().containsKey(key);
  }
  /**
   * Use {@link #getPeopleMapMap()} instead.
   */
  @java.lang.Override
  @java.lang.Deprecated
  public java.util.Map<java.lang.String, org.noahsark.tutorial.protos.Person> getPeopleMap() {
    return getPeopleMapMap();
  }
  /**
   * <code>map&lt;string, .tutorial.Person&gt; people_map = 2;</code>
   */
  @java.lang.Override

  public java.util.Map<java.lang.String, org.noahsark.tutorial.protos.Person> getPeopleMapMap() {
    return internalGetPeopleMap().getMap();
  }
  /**
   * <code>map&lt;string, .tutorial.Person&gt; people_map = 2;</code>
   */
  @java.lang.Override

  public org.noahsark.tutorial.protos.Person getPeopleMapOrDefault(
      java.lang.String key,
      org.noahsark.tutorial.protos.Person defaultValue) {
    if (key == null) { throw new java.lang.NullPointerException(); }
    java.util.Map<java.lang.String, org.noahsark.tutorial.protos.Person> map =
        internalGetPeopleMap().getMap();
    return map.containsKey(key) ? map.get(key) : defaultValue;
  }
  /**
   * <code>map&lt;string, .tutorial.Person&gt; people_map = 2;</code>
   */
  @java.lang.Override

  public org.noahsark.tutorial.protos.Person getPeopleMapOrThrow(
      java.lang.String key) {
    if (key == null) { throw new java.lang.NullPointerException(); }
    java.util.Map<java.lang.String, org.noahsark.tutorial.protos.Person> map =
        internalGetPeopleMap().getMap();
    if (!map.containsKey(key)) {
      throw new java.lang.IllegalArgumentException();
    }
    return map.get(key);
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    for (int i = 0; i < peoples_.size(); i++) {
      output.writeMessage(1, peoples_.get(i));
    }
    com.google.protobuf.GeneratedMessageV3
      .serializeStringMapTo(
        output,
        internalGetPeopleMap(),
        PeopleMapDefaultEntryHolder.defaultEntry,
        2);
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < peoples_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, peoples_.get(i));
    }
    for (java.util.Map.Entry<java.lang.String, org.noahsark.tutorial.protos.Person> entry
         : internalGetPeopleMap().getMap().entrySet()) {
      com.google.protobuf.MapEntry<java.lang.String, org.noahsark.tutorial.protos.Person>
      peopleMap__ = PeopleMapDefaultEntryHolder.defaultEntry.newBuilderForType()
          .setKey(entry.getKey())
          .setValue(entry.getValue())
          .build();
      size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(2, peopleMap__);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof org.noahsark.tutorial.protos.AddressBook)) {
      return super.equals(obj);
    }
    org.noahsark.tutorial.protos.AddressBook other = (org.noahsark.tutorial.protos.AddressBook) obj;

    if (!getPeoplesList()
        .equals(other.getPeoplesList())) return false;
    if (!internalGetPeopleMap().equals(
        other.internalGetPeopleMap())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (getPeoplesCount() > 0) {
      hash = (37 * hash) + PEOPLES_FIELD_NUMBER;
      hash = (53 * hash) + getPeoplesList().hashCode();
    }
    if (!internalGetPeopleMap().getMap().isEmpty()) {
      hash = (37 * hash) + PEOPLE_MAP_FIELD_NUMBER;
      hash = (53 * hash) + internalGetPeopleMap().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.noahsark.tutorial.protos.AddressBook parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.noahsark.tutorial.protos.AddressBook parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.noahsark.tutorial.protos.AddressBook parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.noahsark.tutorial.protos.AddressBook parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.noahsark.tutorial.protos.AddressBook parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.noahsark.tutorial.protos.AddressBook parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.noahsark.tutorial.protos.AddressBook parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.noahsark.tutorial.protos.AddressBook parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.noahsark.tutorial.protos.AddressBook parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static org.noahsark.tutorial.protos.AddressBook parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.noahsark.tutorial.protos.AddressBook parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.noahsark.tutorial.protos.AddressBook parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(org.noahsark.tutorial.protos.AddressBook prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * Our address book file is just one of these.
   * </pre>
   *
   * Protobuf type {@code tutorial.AddressBook}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:tutorial.AddressBook)
      org.noahsark.tutorial.protos.AddressBookOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.noahsark.tutorial.protos.AddressBookProtos.internal_static_tutorial_AddressBook_descriptor;
    }

    @SuppressWarnings({"rawtypes"})
    protected com.google.protobuf.MapField internalGetMapField(
        int number) {
      switch (number) {
        case 2:
          return internalGetPeopleMap();
        default:
          throw new RuntimeException(
              "Invalid map field number: " + number);
      }
    }
    @SuppressWarnings({"rawtypes"})
    protected com.google.protobuf.MapField internalGetMutableMapField(
        int number) {
      switch (number) {
        case 2:
          return internalGetMutablePeopleMap();
        default:
          throw new RuntimeException(
              "Invalid map field number: " + number);
      }
    }
    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.noahsark.tutorial.protos.AddressBookProtos.internal_static_tutorial_AddressBook_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.noahsark.tutorial.protos.AddressBook.class, org.noahsark.tutorial.protos.AddressBook.Builder.class);
    }

    // Construct using org.noahsark.tutorial.protos.AddressBook.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
        getPeoplesFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (peoplesBuilder_ == null) {
        peoples_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        peoplesBuilder_.clear();
      }
      internalGetMutablePeopleMap().clear();
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.noahsark.tutorial.protos.AddressBookProtos.internal_static_tutorial_AddressBook_descriptor;
    }

    @java.lang.Override
    public org.noahsark.tutorial.protos.AddressBook getDefaultInstanceForType() {
      return org.noahsark.tutorial.protos.AddressBook.getDefaultInstance();
    }

    @java.lang.Override
    public org.noahsark.tutorial.protos.AddressBook build() {
      org.noahsark.tutorial.protos.AddressBook result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public org.noahsark.tutorial.protos.AddressBook buildPartial() {
      org.noahsark.tutorial.protos.AddressBook result = new org.noahsark.tutorial.protos.AddressBook(this);
      int from_bitField0_ = bitField0_;
      if (peoplesBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          peoples_ = java.util.Collections.unmodifiableList(peoples_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.peoples_ = peoples_;
      } else {
        result.peoples_ = peoplesBuilder_.build();
      }
      result.peopleMap_ = internalGetPeopleMap();
      result.peopleMap_.makeImmutable();
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof org.noahsark.tutorial.protos.AddressBook) {
        return mergeFrom((org.noahsark.tutorial.protos.AddressBook)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.noahsark.tutorial.protos.AddressBook other) {
      if (other == org.noahsark.tutorial.protos.AddressBook.getDefaultInstance()) return this;
      if (peoplesBuilder_ == null) {
        if (!other.peoples_.isEmpty()) {
          if (peoples_.isEmpty()) {
            peoples_ = other.peoples_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensurePeoplesIsMutable();
            peoples_.addAll(other.peoples_);
          }
          onChanged();
        }
      } else {
        if (!other.peoples_.isEmpty()) {
          if (peoplesBuilder_.isEmpty()) {
            peoplesBuilder_.dispose();
            peoplesBuilder_ = null;
            peoples_ = other.peoples_;
            bitField0_ = (bitField0_ & ~0x00000001);
            peoplesBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getPeoplesFieldBuilder() : null;
          } else {
            peoplesBuilder_.addAllMessages(other.peoples_);
          }
        }
      }
      internalGetMutablePeopleMap().mergeFrom(
          other.internalGetPeopleMap());
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      org.noahsark.tutorial.protos.AddressBook parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.noahsark.tutorial.protos.AddressBook) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<org.noahsark.tutorial.protos.Person> peoples_ =
      java.util.Collections.emptyList();
    private void ensurePeoplesIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        peoples_ = new java.util.ArrayList<org.noahsark.tutorial.protos.Person>(peoples_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        org.noahsark.tutorial.protos.Person, org.noahsark.tutorial.protos.Person.Builder, org.noahsark.tutorial.protos.PersonOrBuilder> peoplesBuilder_;

    /**
     * <code>repeated .tutorial.Person peoples = 1;</code>
     */
    public java.util.List<org.noahsark.tutorial.protos.Person> getPeoplesList() {
      if (peoplesBuilder_ == null) {
        return java.util.Collections.unmodifiableList(peoples_);
      } else {
        return peoplesBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .tutorial.Person peoples = 1;</code>
     */
    public int getPeoplesCount() {
      if (peoplesBuilder_ == null) {
        return peoples_.size();
      } else {
        return peoplesBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .tutorial.Person peoples = 1;</code>
     */
    public org.noahsark.tutorial.protos.Person getPeoples(int index) {
      if (peoplesBuilder_ == null) {
        return peoples_.get(index);
      } else {
        return peoplesBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .tutorial.Person peoples = 1;</code>
     */
    public Builder setPeoples(
        int index, org.noahsark.tutorial.protos.Person value) {
      if (peoplesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensurePeoplesIsMutable();
        peoples_.set(index, value);
        onChanged();
      } else {
        peoplesBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .tutorial.Person peoples = 1;</code>
     */
    public Builder setPeoples(
        int index, org.noahsark.tutorial.protos.Person.Builder builderForValue) {
      if (peoplesBuilder_ == null) {
        ensurePeoplesIsMutable();
        peoples_.set(index, builderForValue.build());
        onChanged();
      } else {
        peoplesBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .tutorial.Person peoples = 1;</code>
     */
    public Builder addPeoples(org.noahsark.tutorial.protos.Person value) {
      if (peoplesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensurePeoplesIsMutable();
        peoples_.add(value);
        onChanged();
      } else {
        peoplesBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .tutorial.Person peoples = 1;</code>
     */
    public Builder addPeoples(
        int index, org.noahsark.tutorial.protos.Person value) {
      if (peoplesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensurePeoplesIsMutable();
        peoples_.add(index, value);
        onChanged();
      } else {
        peoplesBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .tutorial.Person peoples = 1;</code>
     */
    public Builder addPeoples(
        org.noahsark.tutorial.protos.Person.Builder builderForValue) {
      if (peoplesBuilder_ == null) {
        ensurePeoplesIsMutable();
        peoples_.add(builderForValue.build());
        onChanged();
      } else {
        peoplesBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .tutorial.Person peoples = 1;</code>
     */
    public Builder addPeoples(
        int index, org.noahsark.tutorial.protos.Person.Builder builderForValue) {
      if (peoplesBuilder_ == null) {
        ensurePeoplesIsMutable();
        peoples_.add(index, builderForValue.build());
        onChanged();
      } else {
        peoplesBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .tutorial.Person peoples = 1;</code>
     */
    public Builder addAllPeoples(
        java.lang.Iterable<? extends org.noahsark.tutorial.protos.Person> values) {
      if (peoplesBuilder_ == null) {
        ensurePeoplesIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, peoples_);
        onChanged();
      } else {
        peoplesBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .tutorial.Person peoples = 1;</code>
     */
    public Builder clearPeoples() {
      if (peoplesBuilder_ == null) {
        peoples_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        peoplesBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .tutorial.Person peoples = 1;</code>
     */
    public Builder removePeoples(int index) {
      if (peoplesBuilder_ == null) {
        ensurePeoplesIsMutable();
        peoples_.remove(index);
        onChanged();
      } else {
        peoplesBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .tutorial.Person peoples = 1;</code>
     */
    public org.noahsark.tutorial.protos.Person.Builder getPeoplesBuilder(
        int index) {
      return getPeoplesFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .tutorial.Person peoples = 1;</code>
     */
    public org.noahsark.tutorial.protos.PersonOrBuilder getPeoplesOrBuilder(
        int index) {
      if (peoplesBuilder_ == null) {
        return peoples_.get(index);  } else {
        return peoplesBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .tutorial.Person peoples = 1;</code>
     */
    public java.util.List<? extends org.noahsark.tutorial.protos.PersonOrBuilder> 
         getPeoplesOrBuilderList() {
      if (peoplesBuilder_ != null) {
        return peoplesBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(peoples_);
      }
    }
    /**
     * <code>repeated .tutorial.Person peoples = 1;</code>
     */
    public org.noahsark.tutorial.protos.Person.Builder addPeoplesBuilder() {
      return getPeoplesFieldBuilder().addBuilder(
          org.noahsark.tutorial.protos.Person.getDefaultInstance());
    }
    /**
     * <code>repeated .tutorial.Person peoples = 1;</code>
     */
    public org.noahsark.tutorial.protos.Person.Builder addPeoplesBuilder(
        int index) {
      return getPeoplesFieldBuilder().addBuilder(
          index, org.noahsark.tutorial.protos.Person.getDefaultInstance());
    }
    /**
     * <code>repeated .tutorial.Person peoples = 1;</code>
     */
    public java.util.List<org.noahsark.tutorial.protos.Person.Builder> 
         getPeoplesBuilderList() {
      return getPeoplesFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        org.noahsark.tutorial.protos.Person, org.noahsark.tutorial.protos.Person.Builder, org.noahsark.tutorial.protos.PersonOrBuilder> 
        getPeoplesFieldBuilder() {
      if (peoplesBuilder_ == null) {
        peoplesBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            org.noahsark.tutorial.protos.Person, org.noahsark.tutorial.protos.Person.Builder, org.noahsark.tutorial.protos.PersonOrBuilder>(
                peoples_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        peoples_ = null;
      }
      return peoplesBuilder_;
    }

    private com.google.protobuf.MapField<
        java.lang.String, org.noahsark.tutorial.protos.Person> peopleMap_;
    private com.google.protobuf.MapField<java.lang.String, org.noahsark.tutorial.protos.Person>
    internalGetPeopleMap() {
      if (peopleMap_ == null) {
        return com.google.protobuf.MapField.emptyMapField(
            PeopleMapDefaultEntryHolder.defaultEntry);
      }
      return peopleMap_;
    }
    private com.google.protobuf.MapField<java.lang.String, org.noahsark.tutorial.protos.Person>
    internalGetMutablePeopleMap() {
      onChanged();;
      if (peopleMap_ == null) {
        peopleMap_ = com.google.protobuf.MapField.newMapField(
            PeopleMapDefaultEntryHolder.defaultEntry);
      }
      if (!peopleMap_.isMutable()) {
        peopleMap_ = peopleMap_.copy();
      }
      return peopleMap_;
    }

    public int getPeopleMapCount() {
      return internalGetPeopleMap().getMap().size();
    }
    /**
     * <code>map&lt;string, .tutorial.Person&gt; people_map = 2;</code>
     */

    @java.lang.Override
    public boolean containsPeopleMap(
        java.lang.String key) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      return internalGetPeopleMap().getMap().containsKey(key);
    }
    /**
     * Use {@link #getPeopleMapMap()} instead.
     */
    @java.lang.Override
    @java.lang.Deprecated
    public java.util.Map<java.lang.String, org.noahsark.tutorial.protos.Person> getPeopleMap() {
      return getPeopleMapMap();
    }
    /**
     * <code>map&lt;string, .tutorial.Person&gt; people_map = 2;</code>
     */
    @java.lang.Override

    public java.util.Map<java.lang.String, org.noahsark.tutorial.protos.Person> getPeopleMapMap() {
      return internalGetPeopleMap().getMap();
    }
    /**
     * <code>map&lt;string, .tutorial.Person&gt; people_map = 2;</code>
     */
    @java.lang.Override

    public org.noahsark.tutorial.protos.Person getPeopleMapOrDefault(
        java.lang.String key,
        org.noahsark.tutorial.protos.Person defaultValue) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      java.util.Map<java.lang.String, org.noahsark.tutorial.protos.Person> map =
          internalGetPeopleMap().getMap();
      return map.containsKey(key) ? map.get(key) : defaultValue;
    }
    /**
     * <code>map&lt;string, .tutorial.Person&gt; people_map = 2;</code>
     */
    @java.lang.Override

    public org.noahsark.tutorial.protos.Person getPeopleMapOrThrow(
        java.lang.String key) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      java.util.Map<java.lang.String, org.noahsark.tutorial.protos.Person> map =
          internalGetPeopleMap().getMap();
      if (!map.containsKey(key)) {
        throw new java.lang.IllegalArgumentException();
      }
      return map.get(key);
    }

    public Builder clearPeopleMap() {
      internalGetMutablePeopleMap().getMutableMap()
          .clear();
      return this;
    }
    /**
     * <code>map&lt;string, .tutorial.Person&gt; people_map = 2;</code>
     */

    public Builder removePeopleMap(
        java.lang.String key) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      internalGetMutablePeopleMap().getMutableMap()
          .remove(key);
      return this;
    }
    /**
     * Use alternate mutation accessors instead.
     */
    @java.lang.Deprecated
    public java.util.Map<java.lang.String, org.noahsark.tutorial.protos.Person>
    getMutablePeopleMap() {
      return internalGetMutablePeopleMap().getMutableMap();
    }
    /**
     * <code>map&lt;string, .tutorial.Person&gt; people_map = 2;</code>
     */
    public Builder putPeopleMap(
        java.lang.String key,
        org.noahsark.tutorial.protos.Person value) {
      if (key == null) { throw new java.lang.NullPointerException(); }
      if (value == null) { throw new java.lang.NullPointerException(); }
      internalGetMutablePeopleMap().getMutableMap()
          .put(key, value);
      return this;
    }
    /**
     * <code>map&lt;string, .tutorial.Person&gt; people_map = 2;</code>
     */

    public Builder putAllPeopleMap(
        java.util.Map<java.lang.String, org.noahsark.tutorial.protos.Person> values) {
      internalGetMutablePeopleMap().getMutableMap()
          .putAll(values);
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:tutorial.AddressBook)
  }

  // @@protoc_insertion_point(class_scope:tutorial.AddressBook)
  private static final org.noahsark.tutorial.protos.AddressBook DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.noahsark.tutorial.protos.AddressBook();
  }

  public static org.noahsark.tutorial.protos.AddressBook getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<AddressBook>
      PARSER = new com.google.protobuf.AbstractParser<AddressBook>() {
    @java.lang.Override
    public AddressBook parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new AddressBook(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<AddressBook> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<AddressBook> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public org.noahsark.tutorial.protos.AddressBook getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

