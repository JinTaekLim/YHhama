    package com.YH.yeohaenghama.domain.test.controller;

    import com.YH.yeohaenghama.domain.test.dto.OpenApiDto;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RestController;

    import java.io.BufferedReader;
    import java.io.InputStreamReader;
    import java.net.HttpURLConnection;
    import java.net.URL;
    import java.net.URLEncoder;

    import static org.hibernate.query.sqm.tree.SqmNode.log;

    @Slf4j
    @RestController
    public class OpenApiController {

        @PostMapping("/apitest")
        public String callApiWithXml(@RequestBody OpenApiDto req) {
            StringBuffer result = new StringBuffer();

            try {
                  //String encodedMobileOS = URLEncoder.encode(req.getMobileOS(), "UTF-8");
                  String encodedKeyword = URLEncoder.encode(req.getKeyword(), "UTF-8");
    //            String encodedContentTypeId = URLEncoder.encode(req.getContentTypeId(), "UTF-8");
                log.info("keyword = " + req.getKeyword());
                log.info("mobileOs = " + req.getMobileOS());
                log.info("numOfRows = " + req.getNumOfRows());

                String apiUrl = "https://apis.data.go.kr/B551011/KorService1/" +
                        "searchKeyword1?" +
                        "serviceKey=%2B1I%2BbTxxqsKlIjXBgNQX38e6gZOJnlCyPLnkFQUQFrpoCl9tEcII2L%2BvUeJuiaAFf3bN1wly8A6VzOw%2FGz9v7w%3D%3D" +
                        "&numOfRows=" + req.getNumOfRows() +   // 한 페이지 결과수
                        "&pageNo=" + req.getPage() +      // 페이지 번호
                        "&MobileOS=ETC" +   // OS구분 [OS 구분 : IOS (아이폰), AND (안드로이드), WIN (윈도우폰), ETC(기타)]
                        "&MobileApp=AppTest" +  // 서비스명
                        "&_type=json" +
                        "&listYN=Y" +       // 목록 구분 (Y=목록, N=개수)
                        "&arrange=A" +      // 정렬구분 (A=제목순, C=수정일순, D=생성일순) 대표이미지가반드시있는정렬(O=제목순, Q=수정일순, R=생성일순)
                        "&keyword=" + encodedKeyword + // 	검색요청할키워드 : (국문=인코딩필요) 샘플 - 강원
                        "&contentTypeId=" + req.getContentTypeId();    // 관광타입(12:관광지, 14:문화시설, 15:축제공연행사, 25:여행코스, 28:레포츠, 32:숙박, 38:쇼핑, 39:음식점)


                log.info("req = " + req);
                log.info("numOfRows = " + req.getNumOfRows());
                log.info("apiUrl = " + apiUrl);




                URL url = new URL(apiUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

                String returnLine;
                result.append("<xmp>");
                while((returnLine = bufferedReader.readLine()) != null) {
                    result.append(returnLine + "\n");
                }
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result + "</xmp>";
        }
    }
