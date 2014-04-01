package tuples.config;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.annotation.pojo.SpaceProperty;
import com.gigaspaces.metadata.index.SpaceIndexType;
import others.ConfigurationStatus;

/**
 * Created by tobi on 31.03.14.
 */
@SpaceClass(includeProperties = SpaceClass.IncludeProperties.IMPLICIT)
public class ConfigStatusTupel {

    private ConfigurationStatus configurationStatus;
    private Integer id = 1;


    public ConfigStatusTupel() {
    }


    public ConfigStatusTupel(ConfigurationStatus configurationStatus) {
        this.configurationStatus = configurationStatus;
    }

    @SpaceIndex(type= SpaceIndexType.BASIC)
    @SpaceId(autoGenerate = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id){
        this.id=id;
    }

    @SpaceProperty
    public ConfigurationStatus getConfigurationStatus() {
        return configurationStatus;
    }

    public void setConfigurationStatus(String configStatus) {
        this.configurationStatus = configurationStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfigStatusTupel that = (ConfigStatusTupel) o;

        if (configurationStatus != that.configurationStatus) return false;
        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = configurationStatus != null ? configurationStatus.hashCode() : 0;
        result = 31 * result + id.hashCode();
        return result;
    }
}
