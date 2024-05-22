package com.YH.yeohaenghama.domain.chat.controller;

import com.YH.yeohaenghama.domain.chat.model.ChatRoom;
import com.YH.yeohaenghama.domain.chat.repo.ChatRoomRepository;
import com.YH.yeohaenghama.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;

//    @GetMapping("/room")
//    public String rooms(Model model) {
//        return "/chat/room";
//    }


    @GetMapping("/roomFindAccount")
    @ResponseBody
    public List<ChatRoom> roomFindAccount(String accountId) {return chatRoomRepository.getChatRoomsByUserId(accountId);}

    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatRoomRepository.findAllRoom();
    }

    @GetMapping("/roomFindItinerary")
    @ResponseBody
    public ChatRoom roomItinerary(@RequestParam String itineraryId) {
        return chatRoomRepository.findRoomByItineraryId(itineraryId);
    }

    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String itineraryId) {
        log.info("log " + itineraryId);
        return chatService.createRoom(itineraryId);
    }

    @PostMapping("/addUsers")
    @ResponseBody
    public ChatRoom addUsers(@RequestParam String roomId,@RequestParam String user){
        return chatService.addUserToChatRoom(roomId,user);
    }

    @DeleteMapping("/deleteRoom")
    @ResponseBody
    public void deleteRoom(@RequestParam String roomId) {
        chatRoomRepository.deleteChatRoom(roomId);
    }

    @DeleteMapping("/deleteAll")
    @ResponseBody
    public void deleteAll(){
        chatRoomRepository.deleteAll();
    }

//    @GetMapping("/room/enter/{roomId}")
//    public String roomDetail(Model model, @PathVariable String roomId) {
//        model.addAttribute("roomId", roomId);
//        return "/chat/roomdetail";
//    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }
}
