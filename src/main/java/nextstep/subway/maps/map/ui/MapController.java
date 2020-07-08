package nextstep.subway.maps.map.ui;

import nextstep.subway.maps.line.application.LineService;
import nextstep.subway.maps.line.dto.LineResponse;
import nextstep.subway.maps.map.dto.MapResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MapController {
    private LineService lineService;

    public MapController(LineService lineService) {
        this.lineService = lineService;
    }

    @GetMapping("/maps")
    public ResponseEntity<MapResponse> showLineDetail() {
        List<LineResponse> response = lineService.findAllLinesWithStations();
        return ResponseEntity.ok(new MapResponse(response));
    }
}
