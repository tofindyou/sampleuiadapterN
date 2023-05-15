package sample.web;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nexacro.uiadapter.spring.core.NexacroException;
import com.nexacro.uiadapter.spring.core.annotation.ParamDataSet;
import com.nexacro.uiadapter.spring.core.data.NexacroResult;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import sample.service.SampleService;
import sample.vo.SearchConditionVO;
import sample.vo.SampleVO;

@Controller
public class SampleController {
  private Logger logger = LoggerFactory.getLogger(SampleController.class);
  
  @Autowired
  private MessageSource messageSource;
  
  @Resource(name = "sampleService")
  private SampleService sampleService;
  
  @RequestMapping(value = "/selectSampleList.do")
  public NexacroResult selectSampleList(
      @ParamDataSet(name = "input1") SearchConditionVO searchVo
  ) throws Exception
  {
    logger.debug("ds_search >>> " + searchVo);
    
    List<SampleVO> userList = sampleService.selectSampleList(searchVo);
        
    NexacroResult result = new NexacroResult();
        
    result.addDataSet("output1", userList);
        
    return result;
  }
  
  @RequestMapping(value = "/updateSampleList.do")
  public NexacroResult updateSampleList(
      @ParamDataSet(name = "input1") List<SampleVO> updateSampleList
      ,Locale locale
  ) throws Exception
  {
	  try {
	      
	      sampleService.updateSampleList(updateSampleList);
	    
	    }catch( Exception e ) {
	      String msg = "";
	      String exceptionName = "";
	      String causeMsg = "";
	      
	      if( e instanceof NexacroException ) {  // id 값에 null 셋팅 후 강제발생
	        
	        //ServiceImpl 에서 NexacroException 인자값 메시지
	        String sMsg = ((NexacroException) e).getMessage();
	        
	        if(sMsg.equals("id")) {
	          //{0}는 필수입니다_TEST.
	          msg = messageSource.getMessage("nx.valid.required", new String[] { sMsg }, locale);
	            
	          ((NexacroException) e).setErrorCode(-1000);  //화면에서 받을 에러코드
	          ((NexacroException) e).setErrorMsg(msg);  //화면에서 받을 에러메시지
	              
	          throw e;              
	        }
	      
	      }
	      else if( e instanceof FdlException ) {  // 신규 id Generating 오류
	        
	        exceptionName = e.getCause().getClass().getName();
	        causeMsg = e.getCause().getLocalizedMessage();
	          
	        //신규데이터 id 생성시 오류가 발생했습니다.
	        throw new NexacroException("[ "+exceptionName +" ] "+ causeMsg, e, -1, "nx.error.idgnr");
	        
	      }
	      else {  // 이외의 Exception 발생
	        
	        exceptionName = e.getCause().getClass().getName();
	        causeMsg = e.getCause().getLocalizedMessage();
	          
	        //저장중 에러가 발생했습니다.
	        throw new NexacroException("[ "+exceptionName +" ] "+ causeMsg, e, -1, "nx.error.save");
	        
	      }
    }
    
    NexacroResult result = new NexacroResult();
    return result;
  }
  
}