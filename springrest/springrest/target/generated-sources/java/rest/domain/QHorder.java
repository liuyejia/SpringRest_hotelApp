package rest.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QHorder is a Querydsl query type for Horder
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QHorder extends EntityPathBase<Horder> {

    private static final long serialVersionUID = -376343594L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHorder horder = new QHorder("horder");

    public final NumberPath<Double> cost = createNumber("cost", Double.class);

    public final StringPath customername = createString("customername");

    public final StringPath demand = createString("demand");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath indentity = createString("indentity");

    public final StringPath op_account = createString("op_account");

    public final NumberPath<Long> ordertime = createNumber("ordertime", Long.class);

    public final StringPath phone = createString("phone");

    public final NumberPath<Long> posttime = createNumber("posttime", Long.class);

    public final NumberPath<Long> pretime = createNumber("pretime", Long.class);

    public final StringPath purpose = createString("purpose");

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final ListPath<Room, QRoom> roomlist = this.<Room, QRoom>createList("roomlist", Room.class, QRoom.class, PathInits.DIRECT2);

    public final QRoomType roomtype;

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public QHorder(String variable) {
        this(Horder.class, forVariable(variable), INITS);
    }

    public QHorder(Path<? extends Horder> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QHorder(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QHorder(PathMetadata<?> metadata, PathInits inits) {
        this(Horder.class, metadata, inits);
    }

    public QHorder(Class<? extends Horder> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.roomtype = inits.isInitialized("roomtype") ? new QRoomType(forProperty("roomtype")) : null;
    }

}

