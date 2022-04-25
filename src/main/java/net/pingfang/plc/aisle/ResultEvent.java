package net.pingfang.plc.aisle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author 王超
 * @description TODO
 * @date 2022-04-15 11:34
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class ResultEvent {
    final String id;
    final Method method;
}
