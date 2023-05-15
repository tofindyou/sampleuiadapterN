package sample;

import com.nexacro.uiadapter.spring.core.data.DataSetRowTypeAccessor;

// 데이터의 상태값 정보를 위한 Class 생성 및 상속
// : DataSetRowTypeAccessor 를 implements 하는 Class (RowTypeVO) 를 생성 후
// 리스트 역할을 하는 Class (sampleVO) 에 상속

// DataSetRowTypeAccessor
// : 그리드 컨트롤에서 행별로 추가, 수정, 삭제 등의 작업을 수행하는 경우
// API 통신 시 각 Row의 상태를 확인 가능하다.

public class RowTypeVO implements DataSetRowTypeAccessor {
    private int rowType;
  
    @Override
    public int getRowType() {
        return this.rowType;
    }

    @Override
    public void setRowType(int rowType) {
        this.rowType = rowType;
    }
}