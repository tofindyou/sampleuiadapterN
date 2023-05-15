package sample.service;

import java.util.List;
import sample.vo.SearchConditionVO;
import sample.vo.SampleVO;

public interface SampleService {
	//데이터 조회
	List< SampleVO > selectSampleList( SearchConditionVO searchInfo ) throws Exception;
	  
	//데이터 추가/수정/삭제
	void updateSampleList( List<SampleVO> sampleList ) throws Exception;
}