package com.example.kafkastreamsv2.streams;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GeofenceStreams {

    private static final Serde<String> STRING_SERDE = Serdes.String();

    @Autowired
    void buildPipeline(StreamsBuilder streamsBuilder) {
        KStream<String, String> aisStreams = streamsBuilder.stream("ais",
                Consumed.with(STRING_SERDE, STRING_SERDE));

        // stateStore 에서 최근 상태를 가져온다.
        KTable<String, String> prevGeofences = streamsBuilder.table("prevGeofences");

        // 메인 비즈니스 로직을 처리 후 결과를 생성한다.
        aisStreams.join(prevGeofences, (imoNo, geofences) -> imoNo + " : " + geofences).to("geofence");

        // 처리가 끝나면 상태를 업데이트한다.
        aisStreams.join(prevGeofences, (imoNo, geofences) -> imoNo + " : " + geofences).to("prevGeofences");
    }

}
