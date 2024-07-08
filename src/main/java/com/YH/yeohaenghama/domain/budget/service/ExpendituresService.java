package com.YH.yeohaenghama.domain.budget.service;

import com.YH.yeohaenghama.domain.account.entity.Account;
import com.YH.yeohaenghama.domain.account.repository.AccountRepository;
import com.YH.yeohaenghama.domain.budget.dto.*;
import com.YH.yeohaenghama.domain.budget.entity.Budget;
import com.YH.yeohaenghama.domain.budget.entity.Expenditures;
import com.YH.yeohaenghama.domain.budget.entity.ExpendituresGroup;
import com.YH.yeohaenghama.domain.budget.repository.BudgetRepository;
import com.YH.yeohaenghama.domain.budget.repository.ExpendituresRepository;
import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import com.YH.yeohaenghama.domain.itinerary.repository.ItineraryJoinAccountRepository;
import com.YH.yeohaenghama.domain.itinerary.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpendituresService {
    private final BudgetRepository budgetRepository;
    private final ExpendituresRepository expendituresRepository;
    private final ItineraryJoinAccountRepository itineraryJoinAccountRepository;
    private final PlaceRepository placeRepository;
    private final AccountRepository accountRepository;

    public ExpendituresAddDTO.Response expendituresAdd(ExpendituresAddDTO.Request dto) {
        Optional<Budget> budgetOpt = budgetRepository.findById(dto.getBudgetId());
        if (budgetOpt.isEmpty()) { throw new NoSuchElementException("해당 ID를 가진 가계부가 존재하지 않습니다. "); }
        Optional<Place> placeOpt = null;
        if(dto.getPlace() != null) {
            placeOpt = placeRepository.findById(dto.getPlace());
            if (placeOpt.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 장소가 존재하지 않습니다. ");
        }

        Optional<Account> payerOpt = accountRepository.findById(dto.getPayerId());
        List<Account> accountList = accountRepository.findAllById(dto.getAccountId());
        if (payerOpt.isEmpty() || accountList.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 유저가 존재하지 않습니다.");


        if(dto.getExpendituresId() != null && !expendituresRepository.findById(dto.getExpendituresId()).isEmpty()) {
            expendituresRepository.deleteById(dto.getExpendituresId());
        }

        ExpendituresAddDTO expendituresAddDTO = new ExpendituresAddDTO(dto);

        if(placeOpt != null) expendituresAddDTO.setPlace(placeOpt.get());


        Expenditures addExpenditures = expendituresAddDTO.toEntity(budgetOpt.get(),payerOpt.get(),accountList);





        expendituresRepository.save(addExpenditures);

        return null;
    }


    public String expendituresDeleteOne(ExpendituresDeleteDTO.RequestDeleteOne dto){
        Optional<Expenditures> expendituresOpt = expendituresRepository.findById(dto.getExpendituresId());
        if(expendituresOpt.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 지출 금액이 존재하지 않습니다. ");

        expendituresRepository.deleteById(dto.getExpendituresId());

        return "삭제 완료";
    }


    public BudgetCalculateDTO.Response calculate(BudgetCalculateDTO.Request dto) {
        Optional<Expenditures> expendituresOpt = expendituresRepository.findById(dto.getExpendituresId());
        if(expendituresOpt.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 지출이 존재하지 않습니다.");
        if(itineraryJoinAccountRepository.findByAccountIdAndItineraryId(dto.getPayerId(),expendituresOpt.get().getBudget().getItinerary().getId()).isEmpty()) throw new NoSuchElementException("해당 ID를 가진 유저가 존재하지 않습니다 .");

        Optional<Account> payerOpt = accountRepository.findById(dto.getPayerId());
        List<Account> accountList = accountRepository.findByIdIn(dto.getAccountId());

        Integer totalAmount = 0;
        for(ExpendituresGroup expendituresGroup : expendituresOpt.get().getExpendituresGroups()){
            totalAmount += expendituresGroup.getAmount();
        }

        return BudgetCalculateDTO.Response.toEntity(payerOpt.get(),totalAmount,accountList,dto.getAmount());
    }

    public ExpendituresAddDTO.getRequest expendituresShow(Long expenditureId){
        Optional<Expenditures> expendituresOpt = expendituresRepository.findById(expenditureId);
        if(expendituresOpt.isEmpty()) throw new NoSuchElementException("해당 ID를 가진 지출이 없습니다.");
        return ExpendituresAddDTO.getRequest.getExpendituresRequest(expendituresOpt.get());
    }



    public ResponseEntity<String> scanReceipt(MultipartFile file) throws Exception {
        Tesseract tesseract = new Tesseract();

        // OCR 언어 설정 ("kor"는 한국어 코드입니다.)
        tesseract.setLanguage("kor");

        // 임시 파일로 변환
        File tempFile = File.createTempFile("uploaded", ".jpg");
        file.transferTo(tempFile);

        // 이미지에서 텍스트 추출
        String extractedText = tesseract.doOCR(tempFile);

        // 추출된 텍스트 출력
        System.out.println("Extracted Text:");
        System.out.println(extractedText);

        // 결과 반환
        return ResponseEntity.ok(extractedText);

    }
}
