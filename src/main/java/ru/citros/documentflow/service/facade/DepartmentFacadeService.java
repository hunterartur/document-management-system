package ru.citros.documentflow.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.citros.documentflow.dto.organizational_structure.DepartmentDto;
import ru.citros.documentflow.exception.DocumentFlowException;
import ru.citros.documentflow.mapper.organizational_structure.DepartmentMapper;
import ru.citros.documentflow.model.organizational_structure.Department;
import ru.citros.documentflow.repository.GeneralRepository;
import ru.citros.documentflow.service.GeneralService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Слой приложения для работы с dto
 *
 * @author AIshmaev
 */
@Service
public class DepartmentFacadeService {

    @Autowired
    private GeneralService<Department> DepartmentService;

    @Autowired
    private GeneralRepository<Department> departmentRepository;

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * Сохраняет объект
     *
     * @param departmentDto объект клиента
     * @return объект клиента
     */
    public DepartmentDto save(DepartmentDto departmentDto) {
        Department department = departmentMapper.fromDto(departmentDto);
        Department savedDepartment = DepartmentService.save(department);
        return departmentMapper.toDto(savedDepartment);
    }

    /**
     * Обновляет объект
     *
     * @param departmentDto объект клиента
     * @return объект клиента
     */
    public DepartmentDto update(DepartmentDto departmentDto) {
        if (departmentDto.getId() == null) {
            throw new DocumentFlowException("У должности нет id");
        }
        Department department = departmentMapper.fromDto(departmentDto);
        Department savedDepartment = DepartmentService.update(department);
        return departmentMapper.toDto(savedDepartment);
    }

    /**
     * Возвращает список объектов
     *
     * @return список объектов
     */
    public List<DepartmentDto> getAll() {
        List<Department> departments = departmentRepository.getAll();
        return departments.stream().map(departmentMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Возвращает объект по id
     *
     * @param uuid id
     * @return объект клиента
     */
    public DepartmentDto getById(UUID uuid) {
        Department department = departmentRepository.getById(uuid);
        return departmentMapper.toDto(department);
    }

    /**
     * Удаляет объект по id
     *
     * @param uuid id
     */
    public void delete(UUID uuid) {
        DepartmentDto departmentDto = DepartmentDto.newBuilder().setId(uuid).build();
        Department department = departmentMapper.fromDto(departmentDto);
        departmentRepository.delete(department);
    }
}
