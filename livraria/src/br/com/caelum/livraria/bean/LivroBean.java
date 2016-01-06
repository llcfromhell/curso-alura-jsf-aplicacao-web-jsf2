package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;

@ManagedBean
@ViewScoped
public class LivroBean {

	private Livro livro = new Livro();
	private Integer autorId;

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public Livro getLivro() {
		return livro;
	}

	public List<Livro> getLivros() {
		return new DAO<Livro>(Livro.class).listaTodos();
	}

	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public void adicionarAutor() {

		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);
		System.out.println("Usando autor " + autor.getNome());
		this.livro.adicionaAutor(autor);
	}

	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("autor", new FacesMessage("Não é possível incluir livro sem autor."));
			return;
		}

		new DAO<Livro>(Livro.class).adiciona(this.livro);
		
		this.livro = new Livro();
	}
	
	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) {
		
	    String valor = value.toString();
	    if (!valor.startsWith("1")) {
	        throw new ValidatorException(new FacesMessage("Deveria começar com 1"));
	    }
		
		
	}

}
