package com.ego.mapper;

import com.ego.pojo.TbMsItem;
import com.ego.pojo.TbMsItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbMsItemMapper {
    int countByExample(TbMsItemExample example);

    int deleteByExample(TbMsItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbMsItem record);

    int insertSelective(TbMsItem record);

    List<TbMsItem> selectByExample(TbMsItemExample example);

    TbMsItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbMsItem record, @Param("example") TbMsItemExample example);

    int updateByExample(@Param("record") TbMsItem record, @Param("example") TbMsItemExample example);

    int updateByPrimaryKeySelective(TbMsItem record);

    int updateByPrimaryKey(TbMsItem record);
}