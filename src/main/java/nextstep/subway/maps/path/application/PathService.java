package nextstep.subway.maps.path.application;

import nextstep.subway.maps.line.application.LineService;
import nextstep.subway.maps.line.dto.LineResponse;
import nextstep.subway.maps.line.dto.LineStationResponse;
import nextstep.subway.maps.path.domain.PathResult;
import nextstep.subway.maps.path.domain.PathType;
import nextstep.subway.maps.path.dto.PathResponse;
import nextstep.subway.maps.station.dto.StationResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PathService {
    private LineService lineService;
    private GraphService graphService;

    public PathService(LineService lineService, GraphService graphService) {
        this.lineService = lineService;
        this.graphService = graphService;
    }

    public PathResponse findPath(Long source, Long target, PathType type) {
        List<LineResponse> lines = lineService.findAllLinesWithStations();
        PathResult pathResult = graphService.findPath(lines, source, target, type);

        List<LineStationResponse> lineStationResponses = pathResult.extractLineStationResponses(lines);
        List<StationResponse> stationResponses = pathResult.extractStationResponse(lines);
        int duration = lineStationResponses.stream().mapToInt(it -> it.getDuration()).sum();
        int distance = lineStationResponses.stream().mapToInt(it -> it.getDistance()).sum();

        return new PathResponse(stationResponses, duration, distance);
    }
}
