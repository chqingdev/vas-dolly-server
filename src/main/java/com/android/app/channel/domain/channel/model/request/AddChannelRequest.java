package com.android.app.channel.domain.channel.model.request;

import lombok.Data;

import java.util.List;

@Data
public class AddChannelRequest {

    private String path;

    private List<String> channels;
}
