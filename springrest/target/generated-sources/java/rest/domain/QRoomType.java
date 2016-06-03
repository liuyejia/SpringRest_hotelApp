package rest.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QRoomType is a Querydsl query type for RoomType
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QRoomType extends EntityPathBase<RoomType> {

    private static final long serialVersionUID = -718778715L;

    public static final QRoomType roomType = new QRoomType("roomType");

    public final NumberPath<Double> discount = createNumber("discount", Double.class);

    public final StringPath facility = createString("facility");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath introduction = createString("introduction");

    public final StringPath name = createString("name");

    public final StringPath picture = createString("picture");

    public final NumberPath<Double> price = createNumber("price", Double.class);

    public final NumberPath<Integer> remain = createNumber("remain", Integer.class);

    public final NumberPath<Integer> total = createNumber("total", Integer.class);

    public QRoomType(String variable) {
        super(RoomType.class, forVariable(variable));
    }

    public QRoomType(Path<? extends RoomType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRoomType(PathMetadata<?> metadata) {
        super(RoomType.class, metadata);
    }

}

