package com.android.app.channel.domain.channel.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetChannelResponse {

    private List<String> channels;
}
