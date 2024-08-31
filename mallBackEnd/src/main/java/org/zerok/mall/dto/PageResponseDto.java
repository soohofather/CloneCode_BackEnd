package org.zerok.mall.dto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Builder;
import lombok.Data;

@Data
public class PageResponseDto<E> {

    private List<E> dtoList;

    private List<Integer> pageNumList;

    private PageRequestDto pageRequestDto;

    private boolean prev, next;

    private int totalCount, prevPage, nextPage, totalPage, current;

    @Builder(builderMethodName = "withAll")
    public PageResponseDto(List<E> dtoList, PageRequestDto pageRequestDto, long totalCount) {

        this.dtoList = dtoList;
        this.pageRequestDto = pageRequestDto;
        this.totalCount = (int)totalCount;

        // 끝 페이지 계산 end
        int end = (int) (Math.ceil(pageRequestDto.getPage() / 10.0)) * 10;

        int start = end - 9;

        // 마지막 페이지
        int last = (int)(Math.ceil(totalCount/(double)pageRequestDto.getSize()));

        end = end > last ? last: end;

        this.prev = start > 1;

        this.next = totalCount > end * pageRequestDto.getSize();

        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

        if(prev) {
            this.prevPage = start -1;
        }

        if(next) {
            this.nextPage = end + 1;
        }

        this.totalPage = this.pageNumList.size();

        this.current = pageRequestDto.getPage();
    }
}
