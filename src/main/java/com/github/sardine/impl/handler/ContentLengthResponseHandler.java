package com.github.sardine.impl.handler;

import com.github.sardine.impl.SardineException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;

import java.io.IOException;

public class ContentLengthResponseHandler extends ValidatingResponseHandler<Long> {

    @Override
    public Long handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
        StatusLine statusLine = httpResponse.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        if (statusCode < HttpStatus.SC_MULTIPLE_CHOICES)
        {
            Header header = httpResponse.getFirstHeader("Content-Length");
            return Long.parseLong(header.getValue());
        }
        if (statusCode == HttpStatus.SC_NOT_FOUND)
        {
            return -1L;
        }
        throw new SardineException("Unexpected response", statusCode, statusLine.getReasonPhrase());
    }
}
