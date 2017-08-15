package com.superspork.api;

public class EsUserGetWrapper
{
    private String _index;
    private String _type;
    private String _id;
    private String _version;
    private Boolean found;
    private EsUser _source;

    public String get_index() {
        return _index;
    }

    public void set_index(String _index) {
        this._index = _index;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_version() {
        return _version;
    }

    public void set_version(String _version) {
        this._version = _version;
    }

    public Boolean getFound() {
        return found;
    }

    public void setFound(Boolean found) {
        this.found = found;
    }

    public EsUser get_source() {
        return _source;
    }

    public void set_source(EsUser _source) {
        this._source = _source;
    }
}
