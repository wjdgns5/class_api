package ch01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class APIExplorer {

	public static void main(String[] args) throws IOException {

		// 순수 자바 코드로 (클라이언트 측 코딩)
		// 준비물이 필요하다.

		// 1. 서버측 주소가 필요하다 - 경로
		// 단일 쓰레드에서 유용

		// http://localhost:8080/test?name=홍길동&age=20
		// http://localhost:8080/test?name=%ED%99%8D%EA%B8%B8%EB%8F%99&age=20
		// url 인코딩으로 바뀐것이다.
		StringBuilder urlBuilder = new StringBuilder(
				"http://apis.data.go.kr/B552584/UlfptcaAlarmInqireSvc/getUlfptcaAlarmInfo");
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
				+ "=FHUZyA1pPZk1EO2QhqP4MyMmtu27trrd0lMFmki07sCiYaBJmOUGGDZwQ3t%2BE4xuBA7DLiJutH1Qk7Om66rlIQ%3D%3D"); /*
																														 * Service
																														 * Key
																														 */
		urlBuilder.append("&" + URLEncoder.encode("returnType", "UTF-8") + "="
				+ URLEncoder.encode("json", "UTF-8")); /* xml 또는 json */

		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode("100", "UTF-8")); /* 한 페이지 결과 수 */
		urlBuilder
				.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
		urlBuilder.append(
				"&" + URLEncoder.encode("year", "UTF-8") + "=" + URLEncoder.encode("2024", "UTF-8")); /* 측정 연도 */
		urlBuilder.append("&" + URLEncoder.encode("itemCode", "UTF-8") + "="
				+ URLEncoder.encode("PM10", "UTF-8")); /* 미세먼지 항목 구분(PM10, PM25), PM10/PM25 모두 조회할 경우 파라미터 생략 */

		// URL 객체에서 문자열 경로를 넣어서 객체 생성
		// URL.openConnection() 데이터 요청 보내기 - 설정하고

		URL url = new URL(urlBuilder.toString());

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		conn.setRequestMethod("GET"); // 서버에서 자원을 요청한다.
		conn.setRequestProperty("Content-type", "application/json");
		// 성공하면 200, 실패하면 404, 또는 405 가 떨어진다.
		System.out.println("Response code: " + conn.getResponseCode());
		
		// 100 ~ 500 의미 (약속) 100 ~ 200 사이는 성공하는 의미? 
		BufferedReader rd;
		// 만약 getResponseCode()이 200보다 같거나 작고 300보다 작거나 같다면
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else { // 성공하지 않았다면
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println(sb.toString());

	} // end of main

} // end of class
