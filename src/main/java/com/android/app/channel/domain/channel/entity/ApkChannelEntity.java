package com.android.app.channel.domain.channel.entity;


import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ApkChannelEntity {

    private String apkPath;

    private String destFolder;

    private List<String> channels;
}
