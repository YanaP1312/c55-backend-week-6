package net.hackyourfuture.backend.week6.postify.models;

public class StreamRowMapper {
}

//public class StreamRowMapper implements RowMapper<Stream> {
//
//    @Override
//    public Stream mapRow(ResultSet rs, int rowNum) throws SQLException {
//        Stream stream = new Stream();
//        stream.setStreamId(rs.getInt("stream_id"));
//        stream.setUserId(rs.getInt("user_id"));
//        stream.setTrackId(rs.getInt("track_id"));
//        stream.setStreamedAt(rs.getObject("streamed_at", OffsetDateTime.class));
//        return stream;
//    }
//}