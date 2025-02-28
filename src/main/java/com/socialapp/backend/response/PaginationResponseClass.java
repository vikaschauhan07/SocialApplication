package com.socialapp.backend.response;

import java.util.List;

import com.socialapp.backend.model.dto.UsersDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PaginationResponseClass<T> {
	private List<T> content;
	private int pageNumber;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	
}
