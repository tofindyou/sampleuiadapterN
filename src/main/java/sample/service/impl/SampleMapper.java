package sample.service.impl;

import java.util.List;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import sample.vo.SearchConditionVO;
import sample.vo.SampleVO;

@Mapper("sampleMapper")
public interface SampleMapper {
  List< SampleVO >  selectSampleList( SearchConditionVO searchInfo ) throws Exception;

  void insertSampleList( SampleVO sampleData ) throws Exception;
  void updateSampleList( SampleVO sampleData ) throws Exception;
  void deleteSampleList( SampleVO sampleData ) throws Exception;
}