package rest.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QRoomEvaluation is a Querydsl query type for RoomEvaluation
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QRoomEvaluation extends EntityPathBase<RoomEvaluation> {

    private static final long serialVersionUID = -1311946137L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoomEvaluation roomEvaluation = new QRoomEvaluation("roomEvaluation");

    public final StringPath context = createString("context");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QHorder order;

    public final NumberPath<Integer> rank = createNumber("rank", Integer.class);

    public final NumberPath<Long> time = createNumber("time", Long.class);

    public QRoomEvaluation(String variable) {
        this(RoomEvaluation.class, forVariable(variable), INITS);
    }

    public QRoomEvaluation(Path<? extends RoomEvaluation> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QRoomEvaluation(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QRoomEvaluation(PathMetadata<?> metadata, PathInits inits) {
        this(RoomEvaluation.class, metadata, inits);
    }

    public QRoomEvaluation(Class<? extends RoomEvaluation> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order = inits.isInitialized("order") ? new QHorder(forProperty("order"), inits.get("order")) : null;
    }

}

