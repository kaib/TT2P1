package interfaces;

import com.gigaspaces.annotation.pojo.SpaceId;

/**
 * Created by tobi on 31.03.14.
 */
public interface Roxel {
    int getPositionX();

    void setPositionX(int positionX);

    int getPositionY();

    void setPositionY(int positionY);

    @SpaceId
    int getId();

    void setId(int id);
}
