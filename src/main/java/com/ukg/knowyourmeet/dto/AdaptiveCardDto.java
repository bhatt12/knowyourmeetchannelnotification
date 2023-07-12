package com.ukg.knowyourmeet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdaptiveCardDto extends CardDto{

    private String summary;

    @JsonProperty("@type")
    private String type;

    @JsonProperty("@context")
    private String context;
    private String themeColor;
    private List<SectionDTO> sections;
    private List<ActionCardDTO> potentialAction;

    @Data
    public class SectionDTO {
        private String activityTitle;
        private String activitySubtitle;
        private String activityImage;
        private List<FactDTO> facts;
        private boolean markdown;

    }

    @Data
    public class FactDTO {
        private String name;
        private String value;
    }

    @Data
    public class ActionCardDTO {
        private String type;
        private String name;
        private List<InputDTO> inputs;
        private List<ActionDTO> actions;
    }

    @Data
    public class InputDTO {
        private String type;
        private String id;
        private String title;
        private boolean isMultiline;
        private String isMultiSelect;
        private List<ChoiceDTO> choices;
    }

    @Data
    public class ChoiceDTO {
        private String display;
        private String value;
    }

    @Data
    public class ActionDTO {
        private String type;
        private String name;
        private String target;
    }

    @Data
    public class OpenUriDTO {
        private String type;
        private String name;
        private List<TargetDTO> targets;
    }

    @Data
    public class TargetDTO {
        private String os;
        private String uri;
    }

}
