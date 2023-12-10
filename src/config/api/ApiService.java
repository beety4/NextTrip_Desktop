package config.api;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import config.RandModule;
import domain.search.DefaultJson;
import domain.search.TourSpotDTO;
import domain.search.TourSpotDetailDTO;
import domain.search.TourSpotSearchDTO;
import domain.search.TourSpotImgDTO;

public class ApiService {
	private HttpConn httpConn = new HttpConn();
	private ObjectMapper objectMapper = new ObjectMapper();
	
	// Index 화면에 표시될 지역별 랜덤 여행 정보 표시
	public ArrayList<TourSpotDTO> getIndexTour() {
		RandModule randModule = new RandModule();
		// API 요청 보낼 파라미터 설정
		StringBuilder sb = new StringBuilder();
		sb.append("/areaBasedSyncList1");						// 요청 주소
		sb.append("?numOfRows=6");								// 몇개
		sb.append("&pageNo=" + randModule.getRndInt(0, 28));	// 페이지 랜덤 선택
		sb.append("&showflag=1");								// 컨텐츠 표출
		sb.append("&listYN=Y");									// 리스트 목록 보기	
		sb.append("&arrange=O");								// A 제목순
		sb.append("&areaCode=" + randModule.getRndAreaCode());	// 랜덤 지역 코드
		sb.append("&contentTypeId=12");							// 타입 12 -> 관광지
		// 테스트용 인하공전위치 XY : 126.6572   37.44913
		
		// 결과값 담을 List 선언 및 apiConn을 통해 json 가져오기
		List<TourSpotDTO> tourSpotList = null;
		String apiData = httpConn.getJson(sb.toString());
		try {
			// json 데이터 파싱 하여 저장 후 List<T> 변환 하여 저장
			DefaultJson<TourSpotDTO> parsing =  objectMapper.readValue(apiData, new TypeReference<DefaultJson<TourSpotDTO>>() {});
			tourSpotList = parsing.getDTOList();
			System.out.println("Total Content : " + parsing.getTotalCnt());
		}catch(Exception e) { 
			e.printStackTrace();
		}
		
		return (ArrayList<TourSpotDTO>) tourSpotList;
	}
	
	
	// contentId 로 정보 상세보기
	public TourSpotDetailDTO getDetailTourSpot(String contentid) {
		StringBuilder sb = new StringBuilder();
		sb.append("/detailCommon1");
		sb.append("?contentId=" + contentid);
		sb.append("&defaultYN=Y");
		sb.append("&firstImageYN=Y");
		sb.append("&addrinfoYN=Y");
		sb.append("&mapinfoYN=Y");
		sb.append("&overviewYN=Y");
		
		TourSpotDetailDTO tourSpotDetailDTO = null;
		String apiData = httpConn.getJson(sb.toString());
		try {
			DefaultJson<TourSpotDetailDTO> parsing = objectMapper.readValue(apiData, new TypeReference<DefaultJson<TourSpotDetailDTO>>() {});
			tourSpotDetailDTO = parsing.getDTO();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		// 해당 content의 이미지 정보 가져옴
		// 만약 이미지가 없다면(null) -> 기본 이미지로 대체
		ArrayList<TourSpotImgDTO> tourSpotImgList = getTourSpotImgList(contentid);
		if(tourSpotImgList == null) {
			tourSpotImgList = new ArrayList<>();
			
			TourSpotImgDTO tmp = new TourSpotImgDTO();
			if(tourSpotDetailDTO.getFirstimage() == null) {
				tmp.setOriginimgurl("assets/img/noImage.png");
			} else {
				tmp.setOriginimgurl(tourSpotDetailDTO.getFirstimage());
			}
			tourSpotImgList.add(tmp);
		}
		
		tourSpotDetailDTO.setTourSpotImgList(tourSpotImgList);
		return tourSpotDetailDTO;
	}
	
	
	// contnetId로 이미지 정보 가져오기
	public ArrayList<TourSpotImgDTO> getTourSpotImgList(String contentid) {
		StringBuilder sb = new StringBuilder();
		sb.append("/detailImage1");
		sb.append("?contentId=" + contentid);
		sb.append("&imageYN=Y");
		sb.append("&subImageYN=Y");
		
		List<TourSpotImgDTO> tourSpotImgList = null;
		String apiData = httpConn.getJson(sb.toString());
		try {
			DefaultJson<TourSpotImgDTO> parsing = objectMapper.readValue(apiData, new TypeReference<DefaultJson<TourSpotImgDTO>>() {});
			// 요청 API에 이미지가 없을 경우 nul로 반환
			tourSpotImgList = parsing.getDTOList();
		}catch(Exception e) {
			System.out.println("==NO IMAGE==");
			e.printStackTrace();
		}
		
		return (ArrayList<TourSpotImgDTO>) tourSpotImgList;
	}

	
	public ArrayList<TourSpotSearchDTO> getTourSpotSearch(String keyword, String contentTypeId) {
		StringBuilder sb = new StringBuilder();
		sb.append("/searchKeyword1");
		sb.append("?keyword=" + keyword);
		sb.append("&listYN=Y");
		sb.append("&arrange=A");
		sb.append("&contentTypeId=" + contentTypeId);
		
		List<TourSpotSearchDTO> tourSpotSearchList = null;
		String apiData = httpConn.getJson(sb.toString());
		try {
			DefaultJson<TourSpotSearchDTO> parsing = objectMapper.readValue(apiData, new TypeReference<DefaultJson<TourSpotSearchDTO>>() {});
			tourSpotSearchList = parsing.getDTOList();
		}catch(Exception e) {
			e.printStackTrace();
		}
	
		return (ArrayList<TourSpotSearchDTO>) tourSpotSearchList;
	}
	
	
}
