package otaku.shelterforcowards.repository;

import org.springframework.stereotype.Repository;
import otaku.shelterforcowards.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence =0L;


    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        member.setLevel(0);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // null이 반환될 가능성이 있을 때 Optional클래스로 감싼다.
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        // Map의 value 값인 Member들이 반환되어 리스트에 저장된다.
        return new ArrayList<>(store.values());

    }

    // store를 초기화하는 메서드
    public void clearStore() {
        store.clear();
        System.out.println("repo 초기화");
    }
}