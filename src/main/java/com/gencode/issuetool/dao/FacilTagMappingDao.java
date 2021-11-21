package com.gencode.issuetool.dao;

import java.util.List;
import java.util.Optional;

import com.gencode.issuetool.obj.FacilTagMapping;

public interface FacilTagMappingDao extends Dao<FacilTagMapping> {
    void register(List<FacilTagMapping> t);
    long delete(FacilTagMapping t);
    long deleteByRuleId(long id);
    long update(FacilTagMapping t);
}
