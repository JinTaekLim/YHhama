package com.YH.yeohaenghama.domain.chatAI.service;

import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIDTO;
import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIDTO.Response;
import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIPopularArea;
import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIPopularPlace;
import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIQuestionDTO;
import com.YH.yeohaenghama.domain.chatAI.dto.ChatAIShowDTO;
import com.YH.yeohaenghama.domain.chatAI.dto.ChatAnswerDTO;
import com.YH.yeohaenghama.domain.chatAI.entity.ChatAnswer;
import com.YH.yeohaenghama.domain.chatAI.entity.ChatType;
import com.YH.yeohaenghama.domain.chatAI.repository.ChatAIRepository;
import com.YH.yeohaenghama.domain.chatAI.repository.ChatAnswerRepository;
import com.YH.yeohaenghama.domain.chatAI.repository.ChatTypeRepository;
import com.YH.yeohaenghama.domain.diary.entity.Diary;
import com.YH.yeohaenghama.domain.diary.repository.DiaryRepository;
import com.YH.yeohaenghama.domain.itinerary.entity.Itinerary;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryRepository;
import com.YH.yeohaenghama.domain.itinerary.repository.PlaceRepository;
import com.YH.yeohaenghama.domain.openApi.dto.SearchAreaDTO;
import com.YH.yeohaenghama.domain.openApi.dto.SearchAreaDTO.Reqeust;
import com.YH.yeohaenghama.domain.openApi.service.OpenApiService;
import com.YH.yeohaenghama.domain.shorts.entity.Shorts;
import com.YH.yeohaenghama.domain.shorts.repository.ShortsRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatAIInfo2 {

  private final ChatTypeRepository chatTypeRepository;
  private final ChatAnswerRepository chatAnswerRepository;
  private final ChatAIRepository chatAIRepository;
  private final ItineraryRepository itineraryRepository;
  private final ShortsRepository shortsRepository;
  private final PlaceRepository placeRepository;
  private final DiaryRepository diaryRepository;
  private final OpenApiService openApiService;
  private final OpenAiChatModel chatModel;
  private final ChatAnswerService chatAnswerService;
  private final ChatTypeService chatTypeService;

  private final String plus = "너는 대한민국 국내 여행지를 다른 사람들에게 보여주고 일정을 계획하거나 공유할 수 있는 [여행하마] 라는 서비스의 마스코트인 [여행하마] 그 자체야. "
      + "다만 해당 서비스를 이용하는 사람들은 서론을 제외하고 본론만을 간략하게 이야기해주기를 좋아해. 이점을 기억하고 친구처럼 친근하게 반말로 해줘. : ";

  private static final double SIMILARITY_THRESHOLD = 0.5;

  private final String failType = "fail";
  private final String failAnswer = "죄송합니다. 다시 한 번 말씀해주시겠어요?";

  Random random = new Random();

  private static final List<String> AREA_LIST = Arrays.asList(
      "서울특별시", "서울",
      "부산광역시", "부산",
      "대구광역시", "대구",
      "인천광역시", "인천",
      "광주광역시", "광주",
      "대전광역시", "대전",
      "울산광역시", "울산",
      "경기도", "경기",
      "강원도", "강원",
      "충청북도", "충북",
      "충청남도", "충남",
      "전라북도", "전북",
      "전라남도", "전남",
      "경상북도", "경북",
      "경상남도", "경남",
      "제주도", "제주"
  );


  @Transactional
  public ChatAIDTO.Response success(String question, ChatAnswer chatAnswer, List<ChatAIQuestionDTO> answerList) {

    saveQuestionAndAnswer(question, chatAnswer);

    Response response = Response.builder()
        .question(question)
        .answer(chatAnswer.getAnswer())
        .type(chatAnswer.getType().getType())
        .other(answerList)
        .build();

    return getResult(response);
  }

  public ChatAIDTO.Response fail(String question){
//    ChatType chatType = chatTypeService.getType("gpt");
    String answer = chatGpt(question);
//    ChatAnswer chatAnswer = new ChatAnswer();
//    chatAnswer.setAnswer(answer);
//    chatAnswer.setType(chatType);
//    saveQuestionAndAnswer(question,chatAnswer);
    log.info(answer);

    return Response.builder()
        .question(question)
        .answer(answer)
        .type("gpt")
        .build();
  }

  public void saveQuestionAndAnswer(String question, ChatAnswer chatAnswer){
    String answerId = String.valueOf(chatAnswer.getId());
    chatAIRepository.update(question, answerId);
  }

//  public ChatType getFailType(String fail){
//
//    return chatTypeRepository.findByType(fail).orElseGet(() -> {
//      ChatType newChatType = new ChatType(fail);
//      chatTypeRepository.save(newChatType);
//      return newChatType;
//    });
//  }

//  public ChatAnswer getFailAnswer(ChatType chatType){
//    return chatAnswerRepository.findByType(chatType).orElseGet(() -> {
//      ChatAnswer newChatAnswer = new ChatAnswer();
//      newChatAnswer.setAnswer(failAnswer);
//      newChatAnswer.setType(chatType);
//      chatAnswerRepository.save(newChatAnswer);
//      return newChatAnswer;
//    });
//  }


  public String getKeyword(String question) {

    Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
    KomoranResult analyzeResultList = komoran.analyze(question);
    List<Token> tokenList = analyzeResultList.getTokenList();

    String keyword = "";
    for (Token token : tokenList) {
      if (token.getPos().equals("NNP")) {
        System.out.format(token.getMorph());
        keyword = token.getMorph();
        break;
      }
    }

    return findMatchingArea(keyword).orElse(keyword);
  }

  private Optional<String> findMatchingArea(String keyword) {
    return AREA_LIST.stream()
        .filter(area -> jaccardSimilarity(keyword, area) > SIMILARITY_THRESHOLD)
        .findFirst();
  }

  private double jaccardSimilarity(String s1, String s2) {
    Set<String> set1 = new HashSet<>(Arrays.asList(s1.split("")));
    Set<String> set2 = new HashSet<>(Arrays.asList(s2.split("")));

    Set<String> intersection = new HashSet<>(set1);
    intersection.retainAll(set2);

    Set<String> union = new HashSet<>(set1);
    union.addAll(set2);

    return (double) intersection.size() / union.size();
  }













  public ChatAIDTO.Response getResult(Response response){

    String question = response.getQuestion();
    String type = response.getType();

    switch (type) {
      case "message" -> response.setResult(null);
      case "directions" -> response.setResult(getKeyword(question));
      case "popularArea" -> response.setResult(popularArea());
      case "popularPlace" -> response.setResult(popularPlace());

      case "searchShorts" -> response.setResult(searchShorts(getKeyword(question)));
      case "searchItinerary" -> response.setResult(searchItinerary(getKeyword(question)));
      case "searchDiary" -> response.setResult(searchDiary(getKeyword(question)));
      case "searchPlace" -> response.setResult(searchPlace(getKeyword(question)));


      case "randomArea" -> response.setResult(randomArea());
      case "randomPlace" -> response.setResult(randomPlace());
      case "randomShorts" -> response.setResult(randomShorts());
      case "randomItinerary" -> response.setResult(randomItinerary());
      case "randomDiary" -> response.setResult(randomDiary());

      case "searchKeyword" -> {
        SearchAreaDTO.Response search = searchKeyword(getKeyword(question));
        if (search == null) fail(question);
        response.setResult(search);
      }
    }

    if (response.getResult() == null) {
      SearchAreaDTO.Response search = searchKeyword(getKeyword(question));
      if (search == null) fail(question);
      response.setResult(search);
    }

    return response;
  }


  public ChatAIPopularArea.Response popularArea() {
    List<Itinerary> itineraryList = itineraryRepository.findAll();
    return ChatAIPopularArea.Response.ranking(itineraryList);
  }

  public ChatAIPopularPlace.Response popularPlace() {
    List<Place> placeList = placeRepository.findAll();
    return ChatAIPopularPlace.Response.recommend(placeList);
  }

  public SearchAreaDTO.Response searchKeyword(String keyword) {
//    Pageable limit = PageRequest.of(0, 10);
//
//    List<Place> placeList = placeRepository.findByPlaceNameContainingLimit(keyword, limit);
//    List<Itinerary> itineraryAreaList = itineraryRepository.findByAreaLimit(keyword, limit);
//    List<Itinerary> itineraryNameList = itineraryRepository.findByNameLimit(keyword,limit);
//    List<Diary> diaryList = diaryRepository.findByTitleContainingLimit(keyword, limit);
//    List<Shorts> shortsContentList = shortsRepository.findByContent(keyword, limit);
//    List<Shorts> shortsTitleLIst = shortsRepository.findByTitle(keyword, limit);

    return openApiService.searchArea(keyword);
  }

  public List<ChatAIShowDTO.ShortsResponse> searchShorts(String keyword){
    Pageable limit = PageRequest.of(0, 10);

    List<Shorts> shortsContentList = shortsRepository.findByContent(keyword, limit);
    List<Shorts> shortsTitleLIst = shortsRepository.findByTitle(keyword, limit);

    return ChatAIShowDTO.ShortsResponse.ofList(shortsContentList, shortsTitleLIst);
  }

  public List<ChatAIShowDTO.ItineraryResponse> searchItinerary(String keyword) {
    Pageable limit = PageRequest.of(0, 10);

//    List<Itinerary> itineraryAreaList = itineraryRepository.findByAreaLimit(keyword, limit);
//    List<Itinerary> itineraryNameList = itineraryRepository.findByNameLimit(keyword, limit);

    List<Itinerary> itineraryAreaList = new ArrayList<>();
    List<Itinerary> itineraryNameList = new ArrayList<>();

    return ChatAIShowDTO.ItineraryResponse.ofList(itineraryAreaList, itineraryNameList);
  }

  public List<ChatAIShowDTO.DiaryResponse> searchDiary(String keyword) {
    Pageable limit = PageRequest.of(0, 10);
    List<Diary> diaryList = diaryRepository.findByTitleContainingLimit(keyword, limit);

    return ChatAIShowDTO.DiaryResponse.ofList(diaryList);
  }

  public List<ChatAIShowDTO.PlaceResponse> searchPlace(String keyword) {
    Pageable limit = PageRequest.of(0, 10);
    List<Place> placeList = placeRepository.findByPlaceNameContainingLimit(keyword, limit);

    return ChatAIShowDTO.PlaceResponse.ofList(placeList);
  }

  public ChatAIShowDTO.AreaResponse randomArea(){
    int randomIndex = random.nextInt(0,AREA_LIST.size());
    String area = AREA_LIST.get(randomIndex);
    return new ChatAIShowDTO.AreaResponse(area);
  }

  public ChatAIShowDTO.PlaceResponse randomPlace(){
    List<Place> placeList = placeRepository.findAll();
    if (placeList.isEmpty()) return null;
    int randomId = random.nextInt(0,placeList.size());
    return ChatAIShowDTO.PlaceResponse.of(placeList.get(randomId));
  }

  public ChatAIShowDTO.ShortsResponse randomShorts(){
    List<Shorts> shortsList = shortsRepository.findAll();
    if (shortsList.isEmpty()) return null;
    int randomId = random.nextInt(0,shortsList.size());
    return ChatAIShowDTO.ShortsResponse.of(shortsList.get(randomId));
  }

  public ChatAIShowDTO.ItineraryResponse randomItinerary() {
    List<Itinerary> itineraryList = itineraryRepository.findAll();
    if (itineraryList.isEmpty()) return null;
    int randomId = random.nextInt(0,itineraryList.size());
    return ChatAIShowDTO.ItineraryResponse.of(itineraryList.get(randomId));
  }

  public ChatAIShowDTO.DiaryResponse randomDiary() {
    List<Diary> diaryList = diaryRepository.findAll();
    if (diaryList.isEmpty()) return null;
    int randomId = random.nextInt(0,diaryList.size());
    return ChatAIShowDTO.DiaryResponse.of(diaryList.get(randomId));

  }

  public String chatGpt(String question) {
    Prompt prompt = new Prompt(
        plus + question,
        OpenAiChatOptions.builder()
            .withModel("gpt-3.5-turbo")
            .withTemperature(0.4F)
            .withMaxTokens(500)
            .build()
    );

    ChatResponse response = chatModel.call(prompt);

    return response.getResult().getOutput().getContent();
  }
}
