package com.wedevzone.digiparc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.wedevzone.digiparc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubscriberDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubscriberDTO.class);
        SubscriberDTO subscriberDTO1 = new SubscriberDTO();
        subscriberDTO1.setId(1L);
        SubscriberDTO subscriberDTO2 = new SubscriberDTO();
        assertThat(subscriberDTO1).isNotEqualTo(subscriberDTO2);
        subscriberDTO2.setId(subscriberDTO1.getId());
        assertThat(subscriberDTO1).isEqualTo(subscriberDTO2);
        subscriberDTO2.setId(2L);
        assertThat(subscriberDTO1).isNotEqualTo(subscriberDTO2);
        subscriberDTO1.setId(null);
        assertThat(subscriberDTO1).isNotEqualTo(subscriberDTO2);
    }
}
