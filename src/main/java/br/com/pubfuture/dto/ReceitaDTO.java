package br.com.pubfuture.dto;

import java.time.LocalDate;

import br.com.pubfuture.enums.TipoReceita;
import br.com.pubfuture.model.Receita;
import br.com.pubfuture.repository.ReceitaRepository;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReceitaDTO {
	
	private Long id;
	private Double valor;
	private LocalDate dataRecebimento;
	private LocalDate dataRecebimentoEsperado;
	private String descricao;
	private Long contaId;
	private TipoReceita tipoReceita;
		
	public ReceitaDTO(Receita receita) {
		this.id = receita.getId();
		this.valor = receita.getValor();
		this.dataRecebimento = receita.getDataRecebimento();
		this.dataRecebimentoEsperado = receita.getDataRecebimentoEsperado();
		this.descricao = receita.getDescricao();
		this.contaId = receita.getConta().getId();
		this.tipoReceita = receita.getTipoReceita();
	}


	public Receita atualizar(Long id, ReceitaRepository receitaRepository) {
		Receita receita = receitaRepository.findById(id).get();
		receita.setValor(this.valor);
		receita.setDataRecebimento(this.dataRecebimento);
		receita.setDataRecebimentoEsperado(this.dataRecebimentoEsperado);
		receita.setTipoReceita(this.tipoReceita);
		receita.setDescricao(this.descricao);
		return receita;
	}
	
}
