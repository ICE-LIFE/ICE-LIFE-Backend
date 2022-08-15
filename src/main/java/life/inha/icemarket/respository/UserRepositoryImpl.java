package life.inha.icemarket.respository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import life.inha.icemarket.dto.UserListDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static life.inha.icemarket.domain.QUser.user;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public List<UserListDto> getUserList() {
        return queryFactory
                .select(Projections.constructor(UserListDto.class,
                        user.id,
                        user.name,
                        user.email,
                        user.nickname,
                        user.createdAt,
                        user.role))
                        .from(user)
                .orderBy(user.createdAt.desc())
                .fetch();
    }
}
