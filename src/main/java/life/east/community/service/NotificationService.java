package life.east.community.service;

import life.east.community.dto.NotificationDTO;
import life.east.community.dto.PaginationDTO;
import life.east.community.enums.NotificationStatusEnum;
import life.east.community.enums.NotificationTypeEnum;
import life.east.community.exception.CustomizeErrorCode;
import life.east.community.exception.CustomizeException;
import life.east.community.mapper.NotificationMapper;
import life.east.community.mapper.UserMapper;
import life.east.community.model.Notification;
import life.east.community.model.NotificationExample;
import life.east.community.model.User;
import life.east.community.model.UserExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 7777777
 * @date 2019/11/29 23:25:59
 * @description
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO listNotifysByUserId(Long userId, Integer pageNumber, Integer pageSize) {

        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();

        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);

        Integer totalCountOfCreator = Math.toIntExact(notificationMapper.countByExample(notificationExample));

        Integer totalPage;
        if (totalCountOfCreator % pageSize == 0) {
            totalPage = totalCountOfCreator / pageSize;
        } else {
            totalPage = totalCountOfCreator / pageSize + 1;
        }

        if (pageNumber < 1) {
            pageNumber = 1;
        }
        if (pageNumber > totalPage) {
            pageNumber = totalPage;
        }
        paginationDTO.setPagination(totalPage, pageNumber);

        Integer offset = pageSize * ((pageNumber - 1) >= 0 ? (pageNumber - 1) : 0);

        notificationExample.setOrderByClause("gmt_create desc");
        List<Notification> notificationList = notificationMapper.selectByExampleWithRowbounds(notificationExample, new RowBounds(offset, pageSize));

        if (notificationList.size() == 0) {
            return paginationDTO;
        }
//        Set<Long> disUserIds = notificationList.stream().map(notification -> notification.getNotifier()).collect(Collectors.toSet());
//        List<Long> userIds = new ArrayList<>(disUserIds);
//        UserExample userExample = new UserExample();
//        userExample.createCriteria().andIdIn(userIds);
//        List<User> users = userMapper.selectByExample(userExample);
//        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        for (Notification notification : notificationList) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setDescription(NotificationTypeEnum.descriptionOfType(notification.getType()));
            notificationDTOList.add(notificationDTO);
        }


        paginationDTO.setData(notificationDTOList);
        return paginationDTO;
    }


    public NotificationDTO readNotify(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!notification.getReceiver().equals(user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READED.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setDescription(NotificationTypeEnum.descriptionOfType(notification.getType()));

        return notificationDTO;
    }

    public Long unReadNotifyCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }
}
