package com.github.nikcolonel.exception;

public class HttpUnauthorizedException extends RuntimeException
{

	public HttpUnauthorizedException(String body)
	{
		this.body = body;
	}

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 4571118973653363681L;
	private final String		body;

	public String getBody()
	{
		return body;
	}

}
