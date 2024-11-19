package com.proyect.tienda.dao;

import com.proyect.tienda.domain.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface VentaDao extends JpaRepository<Venta, Long> {
    List<Venta> findByCliente_IdClienteAndFechaPedidoBetween(Long clienteId, Date fechaDesde, Date fechaHasta);

    List<Venta> findByCliente_IdClienteAndEstado(int clienteId, String estado);

    Venta findByIdVenta(Long idVenta);

    @Query(value = "SELECT SUM(v.Importe_Total) FROM venta v WHERE v.Importe_Total > 0.00 AND CAST(v.Fecha_Pedido AS DATE) = CURRENT_DATE;", nativeQuery = true)
    BigDecimal sumAbonosDelDiaTotal();

    @Query(value = "SELECT SUM(v.Importe_Total) as total, CAST(v.Fecha_Pedido AS DATE) as fecha \n" +
            "FROM venta v \n" +
            "WHERE v.Importe_Total > 0.00 \n" +
            "AND CAST(v.Fecha_Pedido AS DATE) BETWEEN CURRENT_DATE - INTERVAL '7 days' AND CURRENT_DATE \n" +
            "GROUP BY CAST(v.Fecha_Pedido AS DATE)", nativeQuery = true)
    List<Map<String, Object>> findGananciasDeLaSemanaTotal();
}
