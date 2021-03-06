package me.postaddict.instagram.scraper.request;

import me.postaddict.instagram.scraper.client.Endpoint;
import me.postaddict.instagram.scraper.client.InstaClient;
import me.postaddict.instagram.scraper.mapper.Mapper;
import me.postaddict.instagram.scraper.model.Account;
import me.postaddict.instagram.scraper.model.PageInfo;
import me.postaddict.instagram.scraper.model.PageObject;
import me.postaddict.instagram.scraper.request.parameters.UserParameter;
import okhttp3.Request;

import java.io.InputStream;

public class GetFollowsRequest extends PaginatedRequest<PageObject<Account>, UserParameter> {

    public GetFollowsRequest(InstaClient instaClient, Mapper mapper, DelayHandler delayHandler) {
        super(instaClient, mapper, delayHandler);
    }

    @Override
    protected Request requestInstagram(UserParameter requestParameters, PageInfo pageInfo) {
        return new Request.Builder()
                .url(Endpoint.getFollowsLinkVariables(requestParameters.getUserId(), 200, pageInfo.getEndCursor()))
                .header(Endpoint.REFERER, Endpoint.BASE_URL + "/")
                .build();
    }

    @Override
    protected void updateResult(PageObject<Account> result, PageObject<Account> current) {
        result.getNodes().addAll(current.getNodes());
        result.setPageInfo(current.getPageInfo());
    }

    @Override
    protected PageInfo getPageInfo(PageObject<Account> current) {
        return current.getPageInfo();
    }

    @Override
    protected PageObject<Account> mapResponse(InputStream jsonStream) {
        return getMapper().mapFollow(jsonStream);
    }
}
