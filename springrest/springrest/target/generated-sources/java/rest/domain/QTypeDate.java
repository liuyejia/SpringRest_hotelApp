package rest.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QTypeDate is a Querydsl query type for TypeDate
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QTypeDate extends EntityPathBase<TypeDate> {

    private static final long serialVersionUID = -1222281736L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTypeDate typeDate = new QTypeDate("typeDate");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> indate = createNumber("indate", Long.class);

    public final NumberPath<Integer> number = createNumber("number", Integer.class);

    public final QRoomType roomtype;

    public final NumberPath<Double> total = createNumber("total", Double.class);

    public QTypeDate(String variable) {
        this(TypeDate.class, forVariable(variable), INITS);
    }

    public QTypeDate(Path<? extends TypeDate> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QTypeDate(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QTypeDate(PathMetadata<?> metadata, PathInits inits) {
        this(TypeDate.class, metadata, inits);
    }

    public QTypeDate(Class<? extends TypeDate> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.roomtype = inits.isInitialized("roomtype") ? new QRoomType(forProperty("roomtype")) : null;
    }

}

