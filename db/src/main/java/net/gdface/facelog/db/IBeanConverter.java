package net.gdface.facelog.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author guyadong
 *
 * @param <G> general type
 * @param <N> native type
 */
public interface IBeanConverter<G,N> {

	/**
	 * Default abstract implementation of {@link IBeanConverter}<br>
	 * 
	 * @author guyadong
	 *
	 * @param <G> general type
	 * @param <N> native type
	 */
	public static abstract class  AbstractHandle <G,N>implements IBeanConverter<G, N> {
		public AbstractHandle() {
		}

		@Override
		public N[] toNative(G[] beans) {
			if(null==beans)return null;
			@SuppressWarnings("unchecked")
			N[] na = (N[]) new Object[beans.length];
			for(int i=0;i<beans.length;++i){
				na[i]=this.toNative(beans[i]);
			}				
			return na;
		}

		@Override
		public G[] fromNative(N[] beans) {
			if(null==beans)return null;
			@SuppressWarnings("unchecked")
			G[] na = (G[]) new Object[beans.length];
			for(int i=0;i<beans.length;++i){
				na[i]=this.fromNative(beans[i]);
			}				
			return na;
		}

		@Override
		public List<N> toNative(Collection<G> beans) {
			if(null==beans)return null;
			ArrayList<N> na = new ArrayList<N>();
			for(G g:beans){
				na.add(this.toNative(g));
			}
			return na;
		}

		@Override
		public List<G> fromNative(Collection<N> beans) {
			if(null==beans)return null;
			ArrayList<G> na = new ArrayList<G>();
			for(N n:beans){
				na.add(this.fromNative(n));
			}
			return na;
		}
	}
	public G fromNative(N bean);
	public N toNative( G bean);
	public N[] toNative(G[] beans);
	public G[] fromNative(N[] beans);
	public List<N> toNative(Collection<G> beans);
	public List<G> fromNative(Collection<N> beans);

}
