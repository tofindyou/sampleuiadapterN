package sample.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sample.service.SampleService;
import sample.vo.SearchConditionVO;
import sample.vo.SampleVO;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

import com.nexacro.java.xapi.data.DataSet;
import com.nexacro.uiadapter.spring.core.data.DataSetRowTypeAccessor;

@Service("sampleService")
public class SampleServiceImpl implements SampleService {
  private Logger logger = LoggerFactory.getLogger(SampleServiceImpl.class);
  
  // id 채번시 사용할 EgovIdGnrService
  /** ID Generation */
  @Resource(name = "egovIdGnrService")
  private EgovIdGnrService egovIdGnrService;
  
  @Resource(name = "sampleMapper")
  private SampleMapper sampleMapper;
  
  @Override
  public List<SampleVO> selectSampleList(SearchConditionVO searchInfo) throws Exception {
    return sampleMapper.selectSampleList(searchInfo);
  }
  
    @Override
    public void updateSampleList(List<SampleVO> sampleList) throws Exception {
    int size = sampleList.size();
    for (int i=0; i<size; i++) {
      SampleVO sample = sampleList.get(i);
        
      if(sample instanceof DataSetRowTypeAccessor) {
        DataSetRowTypeAccessor accessor = (DataSetRowTypeAccessor) sample;
            
        if(accessor.getRowType() == DataSet.ROW_TYPE_INSERTED) {
          String id = null;
          
            //getNextStringId 사용시 FdlException 이 발생할수있어 필수 catch
            id = egovIdGnrService.getNextStringId();
            
            sample.setId(id);
            
            sampleMapper.insertSampleList(sample);
                
        }else if(accessor.getRowType() == DataSet.ROW_TYPE_UPDATED) {
          sampleMapper.updateSampleList(sample);              
        }else if(accessor.getRowType() == DataSet.ROW_TYPE_DELETED) {
          sampleMapper.deleteSampleList(sample);     
          
        } // else if 문
      } // if 문
      
    } // for 문
    } // updateSampleList 메서드
}