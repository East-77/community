package life.east.community.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 7777777
 * @date 2019/11/24 20:28:38
 * @description 分页
 */
@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private Boolean showPrevious;
    private Boolean showFirstPage;
    private Boolean showNext;
    private Boolean showEndPage;
    private Integer currentPage;
    private Integer totalPage;
    private List<Integer> pages = new ArrayList<>();//pages的这个初始化默认值new ArrayList<>()去掉会出现异常

    public void setPagination(Integer totalPage, Integer pageNumber, Integer pageSize) {
        this.currentPage = pageNumber;
        this.totalPage = totalPage;
//        Integer totalPage;
//        if (totalCount % pageSize == 0) {
//            totalPage = totalCount / pageSize;
//        } else {
//            totalPage = totalCount / pageSize + 1;
//        }

        //页码导航栏显示逻辑。最多显示七个页码
        System.out.println(pageNumber);
        pages.add(pageNumber);
        for (int i = 1; i <= 3; i++) {
            if (pageNumber - i > 0) {
                //头部追加
                pages.add(0, pageNumber - i);
            }
            if (pageNumber + i <= totalPage) {
                //尾部追加
                pages.add(pageNumber + i);
            }
        }

        //当显示的是第一页时，跳转到上一页的按钮不展现
        if (pageNumber == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }
        //当到达最后一页时，跳转到下一页的按钮不展示
        if (pageNumber.equals(totalPage)) {
            showNext = false;
        } else {
            showNext = true;
        }
        //当页码导航里没有第一页时，跳转到最后一页的按钮才展现
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }
        //当页码导航里没有最后时，跳转到最后一页的按钮展现
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }

    }
}
