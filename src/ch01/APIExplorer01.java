package ch01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class APIExplorer01 {

	public static void main(String[] args) throws IOException {
		// StringBuilder : 가변한 문자열을 처리하기 위한 클래스 이다. StringBuffer과 비슷?
		StringBuilder urlBuilder = new StringBuilder(
				"http://apis.data.go.kr/1160100/service/GetStocDiviInfoService/getDiviInfo"); /* URL */

		// "?" : GET 변수쿼리의 시작, DB에서 데이터를 요청
		// 쿼리? : 문의하다, 질문하다라는 (요청)뜻
		// URLEncoder : URL을 서버가 이해할 수 있는 표준 형식으로 변환
		// Encoder(인코딩) : 코드화, 암호화를 의미 한자어 표현으로 부호화(符號化)

		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
				+ "=FHUZyA1pPZk1EO2QhqP4MyMmtu27trrd0lMFmki07sCiYaBJmOUGGDZwQ3t%2BE4xuBA7DLiJutH1Qk7Om66rlIQ%3D%3D"); /*
																														 * Service
																														 * Key
																														 */
		// "&" : 쿼리(요청)할 문자열의 값
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8")
				+ "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
		
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8")
				+ "=" + URLEncoder.encode("1", "UTF-8")); /* 한 페이지 결과 수 */
		
		urlBuilder.append("&" + URLEncoder.encode("resultType", "UTF-8")
				+ "=" + URLEncoder.encode("json", "UTF-8")); /* 결과형식(xml/json) */
		
		urlBuilder.append("&" + URLEncoder.encode("basDt", "UTF-8")
				+ "=" + URLEncoder.encode("", "UTF-8")); /* 작업 또는 거래의 기준이 되는 일자(년월일) */
		
//		urlBuilder.append("&" + URLEncoder.encode("crno", "UTF-8") + "="
//				+ URLEncoder.encode("", "UTF-8")); /* 법인등록번호 */
		
		urlBuilder.append("&" + URLEncoder.encode("stckIssuCmpyNm", "UTF-8")
				+ "=" + URLEncoder.encode("현대에너지솔루션", "UTF-8")); /* 주식발행사의 명칭 */

		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
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
	}

}
