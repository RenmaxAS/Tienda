package com.proyect.tienda.dao;

import com.proyect.tienda.domain.Abono;
import com.proyect.tienda.domain.Venta;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
@Repository
public interface AbonoDao extends JpaRepository<Abono, Long> {

    List<Abono> findByVenta_IdVenta(Long ventaId);

    @Query(value = "SELECT SUM(a.abono) FROM abono a WHERE a.abono > 0.00 AND a.fecha_abono = CURRENT_DATE", nativeQuery = true)
    BigDecimal sumAbonosDelDiaReal();

    @Query(value = "SELECT SUM(a.abono) AS total, a.fecha_abono AS fecha FROM abono a\n" +
            "WHERE a.abono > 0.00 AND a.fecha_abono BETWEEN CURRENT_DATE - INTERVAL '7 days' AND CURRENT_DATE\n" +
            "GROUP BY a.fecha_abono", nativeQuery = true)
    List<Map<String, Object>> findGananciasDeLaSemanaReal();

    @Query(value = "SELECT deuda FROM abono WHERE id_venta = :ventaId ORDER BY fecha_abono DESC, id DESC LIMIT 1", nativeQuery = true)
    BigDecimal findUltimaDeudaByVentaId(@Param("ventaId") Long ventaId);
}
