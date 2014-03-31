package tuples.config;

import com.gigaspaces.annotation.pojo.SpaceId;
import others.ConfigurationStatus;

/**
 * Created by tobi on 31.03.14.
 */
public class ConfigStatusTupel {

    private ConfigurationStatus configurationStatus;
    private Integer id = 0;


    public ConfigStatusTupel() {
    }


    public ConfigStatusTupel(ConfigurationStatus configurationStatus) {
        this.configurationStatus = configurationStatus;
    }

    @SpaceId
    public Integer getId() {
        return id;
    }

    public void setId(Integer id){
        this.id=id;
    }

    public ConfigurationStatus getConfigurationStatus() {
        return configurationStatus;
    }

    public void setConfigurationStatus(ConfigurationStatus configStatus) {
        this.configurationStatus = configurationStatus;
    }
}
