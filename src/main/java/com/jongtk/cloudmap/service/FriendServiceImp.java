package com.jongtk.cloudmap.service;

import com.jongtk.cloudmap.dto.FriendDTO;
import com.jongtk.cloudmap.entity.Friend;
import com.jongtk.cloudmap.entity.FriendPost;
import com.jongtk.cloudmap.entity.Member;
import com.jongtk.cloudmap.repository.FriendPostRepository;
import com.jongtk.cloudmap.repository.FriendRepository;
import com.jongtk.cloudmap.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendServiceImp implements FriendService {

    private final MemberRepository memberRepository;
    private final FriendRepository friendRepository;
    private final FriendPostRepository friendPostRepository;


    @Override
    public List<FriendDTO> getFreindList(String username) {
        Optional<Member> memberOp = memberRepository.findById(username);

        List<FriendDTO> result = new ArrayList<>();

        if (memberOp.isPresent()) {
            List<Friend> friendList = friendRepository.findByMember(memberOp.get());

            result =
            friendList.stream().map(friend -> {
                FriendDTO friendDTO = FriendDTO.builder()
                        .name(friend.getFriend().getName())
                        .email(friend.getFriend().getEmail())
                        .profileImg(friend.getFriend().getProfileImg())
                        .build();

                return friendDTO;
            }).collect(Collectors.toList());
        }

        return result;
    }

    @Override
    public List<FriendDTO> getReceiveList(String username) {
        Optional<Member> memberOp = memberRepository.findById(username);

        List<FriendDTO> result = new ArrayList<>();

        if (memberOp.isPresent()) {
            List<FriendPost> friendPostList = friendPostRepository.findByReceiver(memberOp.get());

            result =
            friendPostList.stream().map(friendPost -> {
                FriendDTO friendDTO = FriendDTO.builder()
                        .name(friendPost.getSender().getName())
                        .email(friendPost.getSender().getEmail())
                        .profileImg(friendPost.getSender().getProfileImg())
                        .build();

                return friendDTO;
            }).collect(Collectors.toList());

            return result;
        }

        return result;
    }

    @Override
    public List<FriendDTO> getSearch(String username, String str) {
        return null;
    }

    @Override
    public boolean postFreind(String username, String targetEmail) {

        Optional<Member> memberOp = memberRepository.findById(username);
        Optional<Member> targetOp = memberRepository.findById(targetEmail);

        if(memberOp.isPresent() && targetOp.isPresent()) {
            boolean isFriend = friendRepository.existsByMemberAndFriend(memberOp.get(), targetOp.get());
            boolean posted = friendPostRepository.existsBySenderAndReceiver(memberOp.get(), targetOp.get());
            boolean postedr = friendPostRepository.existsBySenderAndReceiver(targetOp.get(), memberOp.get());

            if(isFriend || posted || postedr){
                return false;
            }

            FriendPost friendPost = FriendPost.builder()
                    .sender(memberOp.get())
                    .receiver(targetOp.get())
                    .build();

            friendPostRepository.save(friendPost);
        }

        return false;
    }

    @Override
    public boolean acceptFreind(String username, String targetEmail) {

        Optional<Member> memberOp = memberRepository.findById(username);
        Optional<Member> targetOp = memberRepository.findById(targetEmail);

        if(memberOp.isPresent() && targetOp.isPresent()) {
            if (friendPostRepository.existsBySenderAndReceiver(targetOp.get(), memberOp.get())){
                Friend friend = Friend.builder()
                        .member(memberOp.get())
                        .friend(targetOp.get())
                        .build();
                Friend friendr = Friend.builder()
                        .member(targetOp.get())
                        .friend(memberOp.get())
                        .build();
                friendRepository.save(friend);
                friendRepository.save(friendr);

                friendPostRepository.deleteBySenderAndReceiver(targetOp.get(), memberOp.get());

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean refuseFreind(String username, String targetEmail) {
        return false;
    }

    @Override
    public boolean deleteFreind(String username, String targetEmail) {
        return false;
    }

    @Override
    public boolean isFreind(String username, String targetEmail) {
        return false;
    }
}