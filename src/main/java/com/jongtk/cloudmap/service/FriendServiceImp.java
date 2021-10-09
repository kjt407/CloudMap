package com.jongtk.cloudmap.service;

import com.jongtk.cloudmap.dto.FriendDTO;
import com.jongtk.cloudmap.entity.Friend;
import com.jongtk.cloudmap.entity.FriendPost;
import com.jongtk.cloudmap.entity.Member;
import com.jongtk.cloudmap.repository.FriendPostRepository;
import com.jongtk.cloudmap.repository.FriendRepository;
import com.jongtk.cloudmap.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
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

        List<FriendDTO> result = new ArrayList<>();

        Optional<Member> memberOp = memberRepository.findById(username);
        List<Member> targetList = memberRepository.findByNameOrEmail(str);

        if(memberOp.isPresent() && !targetList.isEmpty()){
            Member member = memberOp.get();

            result =
            targetList.stream().map(target -> {

                String state = null;
                if(friendRepository.existsByMemberAndFriend(member, target)){
                    state = "friend";
                } else if (friendPostRepository.existsBySenderAndReceiver(target,member)){
                    state = "received";
                } else if (friendPostRepository.existsBySenderAndReceiver(member, target)){
                    state = "sent";
                } else {
                    state = "no";
                }
                FriendDTO friendDTO = FriendDTO.builder()
                        .email(target.getEmail())
                        .name(target.getName())
                        .profileImg(target.getProfileImg())
                        .state(state)
                        .build();

                return friendDTO;
            }).collect(Collectors.toList());
        }

        return result;
    }

    @Override
    public boolean postFreind(String username, String targetEmail) {

        Optional<Member> memberOp = memberRepository.findById(username);
        Optional<Member> targetOp = memberRepository.findById(targetEmail);

        if(memberOp.isPresent() && targetOp.isPresent()) {
            log.warn("타겟 멤버 둘다 존재함");
            boolean isFriend = friendRepository.existsByMemberAndFriend(memberOp.get(), targetOp.get());
            boolean posted = friendPostRepository.existsBySenderAndReceiver(memberOp.get(), targetOp.get());
            boolean postedr = friendPostRepository.existsBySenderAndReceiver(targetOp.get(), memberOp.get());

            if(isFriend || posted || postedr){
                log.warn("이미 신청이 되었거나 친구상태임");
                return false;
            }

            FriendPost friendPost = FriendPost.builder()
                    .sender(memberOp.get())
                    .receiver(targetOp.get())
                    .build();

            friendPostRepository.save(friendPost);

            return true;
        }

        return false;
    }

    @Override
    public boolean acceptFreind(String username, String targetEmail) {

        Optional<Member> memberOp = memberRepository.findById(username);
        Optional<Member> targetOp = memberRepository.findById(targetEmail);

        if(memberOp.isPresent() && targetOp.isPresent()) {
            Optional<FriendPost> fp = friendPostRepository.findBySenderAndReceiver(targetOp.get(), memberOp.get());
            if (fp.isPresent()){
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

                friendPostRepository.delete(fp.get());

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean refuseFreind(String username, String targetEmail) {

        Optional<Member> memberOp = memberRepository.findById(username);
        Optional<Member> targetOp = memberRepository.findById(targetEmail);

        if(memberOp.isPresent() && targetOp.isPresent()) {
            Optional<FriendPost> fp = friendPostRepository.findBySenderAndReceiver(targetOp.get(), memberOp.get());
            if (fp.isPresent()){
                friendPostRepository.delete(fp.get());
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean deleteFreind(String username, String targetEmail) {
        Optional<Member> memberOp = memberRepository.findById(username);
        Optional<Member> targetOp = memberRepository.findById(targetEmail);

        if(memberOp.isPresent() && targetOp.isPresent()) {
            if(friendRepository.existsByMemberAndFriend(memberOp.get(), targetOp.get())){
                Optional<Friend> friend = friendRepository.findByMemberAndFriend(memberOp.get(), targetOp.get());
                Optional<Friend> friendr = friendRepository.findByMemberAndFriend(targetOp.get(), memberOp.get());

                if(friend.isPresent() && friendr.isPresent()){
                    friendRepository.delete(friend.get());
                    friendRepository.delete(friendr.get());

                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean isFreind(String username, String targetEmail) {
        return false;
    }
}
