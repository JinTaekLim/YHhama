//package com.YH.yeohaenghama.domain.test.service;
//
//import com.YH.yeohaenghama.domain.test.dto.PocketDTO;
//import com.YH.yeohaenghama.domain.test.dto.StoneDTO;
//import com.YH.yeohaenghama.domain.test.entity.Pocket;
//import com.YH.yeohaenghama.domain.test.entity.Stone;
//import com.YH.yeohaenghama.domain.test.repository.PocketRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class PocketService {
//    private final PocketRepository pocketRepository;
//
//    public void createPocketAndStones(PocketDTO pocketDTO) {
//        Pocket pocket = Pocket.createPocket(
//                pocketDTO.getPocketName(), pocketDTO.getPocketColor()
//        );
//
//        List<StoneDTO> stones = pocketDTO.getStones();
//        for (StoneDTO stoneDTO : stones) {
//            Stone stone = Stone.createStone(stoneDTO.getStoneName(), pocket);
//            pocket.putStone(stone);
//        }
//        pocketRepository.save(pocket);
//    }
//}
