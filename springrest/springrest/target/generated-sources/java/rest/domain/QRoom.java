package rest.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QRoom is a Querydsl query type for Room
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QRoom extends EntityPathBase<Room> {

    private static final long serialVersionUID = -706238261L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoom room = new QRoom("room");

    public final NumberPath<Integer> floor = createNumber("floor", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QHorder orderid;

    public final StringPath roomnumber = createString("roomnumber");

    public final QRoomType roomtype;

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public QRoom(String variable) {
        this(Room.class, forVariable(variable), INITS);
    }

    public QRoom(Path<? extends Room> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QRoom(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QRoom(PathMetadata<?> metadata, PathInits inits) {
        this(Room.class, metadata, inits);
    }

    public QRoom(Class<? extends Room> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.orderid = inits.isInitialized("orderid") ? new QHorder(forProperty("orderid"), inits.get("orderid")) : null;
        this.roomtype = inits.isInitialized("roomtype") ? new QRoomType(forProperty("roomtype")) : null;
    }

}

