import { useState, useEffect } from 'react'
import axios from 'axios'
import { Plus, Trash2, Edit3, X, Save, RefreshCw, Bike, Search, Filter, ArrowUpRight, Gauge, DollarSign, Calendar } from 'lucide-react'

const API_BASE = '/motos'

function App() {
  const [motos, setMotos] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [isModalOpen, setIsModalOpen] = useState(false)
  const [editingMoto, setEditingMoto] = useState(null)
  const [searchTerm, setSearchTerm] = useState('')

  // Form State
  const [formData, setFormData] = useState({
    id: '',
    brand: '',
    model: '',
    year: new Date().getFullYear(),
    displacement: '',
    color: '',
    price: '',
    category: ''
  })

  useEffect(() => {
    fetchMotos()
  }, [])

  const fetchMotos = async () => {
    setLoading(true)
    try {
      const response = await axios.get(API_BASE)
      setMotos(response.data)
      setError(null)
    } catch (err) {
      setError('Falha na conexão. O backend está rodando?')
    } finally {
      setTimeout(() => setLoading(false), 600) // Small delay for smoother transition
    }
  }

  const handleInputChange = (e) => {
    const { name, value } = e.target
    setFormData(prev => ({ ...prev, [name]: value }))
  }

  const openForm = (moto = null) => {
    if (moto) {
      setEditingMoto(moto)
      setFormData(moto)
    } else {
      setEditingMoto(null)
      setFormData({
        id: '',
        brand: '',
        model: '',
        year: new Date().getFullYear(),
        displacement: '',
        color: '',
        price: '',
        category: ''
      })
    }
    setIsModalOpen(true)
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    try {
      if (editingMoto) {
        await axios.put(`${API_BASE}/${formData.id}`, formData)
      } else {
        await axios.post(API_BASE, formData)
      }
      setIsModalOpen(false)
      fetchMotos()
    } catch (err) {
      alert('Erro ao salvar os dados.')
    }
  }

  const handleDelete = async (id) => {
    if (window.confirm('Deseja realmente remover esta motocicleta?')) {
      try {
        await axios.delete(`${API_BASE}/${id}`)
        fetchMotos()
      } catch (err) {
        alert('Erro ao excluir.')
      }
    }
  }

  const filteredMotos = motos.filter(m =>
    m.model.toLowerCase().includes(searchTerm.toLowerCase()) ||
    m.brand.toLowerCase().includes(searchTerm.toLowerCase())
  )

  return (
    <div className="min-h-screen bg-[#020617] text-slate-100 font-sans selection:bg-blue-500/30">
      {/* Dynamic Background */}
      <div className="fixed inset-0 overflow-hidden pointer-events-none -z-10">
        <div className="absolute top-[-10%] left-[-10%] w-[40%] h-[40%] bg-blue-600/10 rounded-full blur-[120px] animate-pulse"></div>
        <div className="absolute bottom-[-10%] right-[-10%] w-[30%] h-[30%] bg-indigo-600/10 rounded-full blur-[100px] animate-pulse" style={{ animationDelay: '2s' }}></div>
      </div>

      <div className="max-w-7xl mx-auto px-6 py-12">
        {/* Header Section */}
        <header className="flex flex-col md:flex-row justify-between items-center mb-16 gap-8">
          <div className="flex items-center gap-5 group cursor-default">
            <div className="relative">
              <div className="absolute inset-0 bg-blue-500 blur-xl opacity-20 group-hover:opacity-40 transition-opacity"></div>
              <div className="relative bg-gradient-to-br from-blue-500 to-indigo-600 p-4 rounded-2xl shadow-2xl rotate-[-5deg] group-hover:rotate-0 transition-transform duration-500">
                <Bike size={36} className="text-white" />
              </div>
            </div>
            <div>
              <h1 className="text-4xl font-black bg-clip-text text-transparent bg-gradient-to-r from-white via-slate-200 to-slate-400">
                LUCAS CASTRO - CRUD
              </h1>
            </div>
          </div>

          <div className="flex items-center gap-4 w-full md:w-auto">
            <div className="relative flex-grow">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-500" size={18} />
              <input
                type="text"
                placeholder="Buscar modelo ou marca..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="w-full md:w-72 bg-slate-900/50 border border-slate-800 rounded-xl py-2.5 pl-10 pr-4 focus:border-blue-500/50 focus:ring-4 focus:ring-blue-500/10 transition-all outline-none backdrop-blur-md"
              />
            </div>
            <button
              onClick={() => openForm()}
              className="flex items-center gap-2 bg-blue-600 hover:bg-blue-500 active:scale-95 transition-all font-bold py-2.5 px-6 rounded-xl shadow-lg shadow-blue-500/25 whitespace-nowrap"
            >
              <Plus size={20} />
              NOVA MOTO
            </button>
          </div>
        </header>

        {/* Global Loading State */}
        {loading ? (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            {[1, 2, 3].map(i => (
              <div key={i} className="h-72 bg-slate-900/50 rounded-3xl animate-pulse border border-slate-800"></div>
            ))}
          </div>
        ) : error ? (
          <div className="bg-red-500/5 border border-red-500/20 p-12 rounded-3xl text-center transform hover:scale-[1.01] transition-transform backdrop-blur-md">
            <div className="inline-block p-4 bg-red-500/10 rounded-full mb-6">
              <RefreshCw size={32} className="text-red-500" />
            </div>
            <h2 className="text-xl font-bold mb-2">Ops! Algo deu errado.</h2>
            <p className="text-slate-400 mb-8 max-w-sm mx-auto">{error}</p>
            <button
              onClick={fetchMotos}
              className="bg-slate-800 hover:bg-slate-700 px-8 py-3 rounded-xl font-bold transition-all border border-slate-700"
            >
              TENTAR NOVAMENTE
            </button>
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            {filteredMotos.length === 0 ? (
              <div className="col-span-full py-32 text-center text-slate-500 border-2 border-dashed border-slate-800 rounded-3xl">
                <p className="text-lg italic font-medium">Nenhuma motocicleta encontrada hoje.</p>
              </div>
            ) : (
              filteredMotos.map((moto, index) => (
                <div
                  key={moto.id}
                  className="group relative bg-slate-900/40 border border-slate-800 rounded-[2rem] p-8 hover:bg-slate-900/60 hover:border-blue-500/30 transition-all duration-500 shadow-xl overflow-hidden backdrop-blur-md"
                  style={{ animationDelay: `${index * 100}ms` }}
                >
                  {/* Decorative element */}
                  <div className="absolute top-0 right-0 w-32 h-32 bg-blue-500/5 blur-3xl -z-10 group-hover:bg-blue-500/10 transition-colors"></div>

                  <div className="flex justify-between items-start mb-6">
                    <span className="px-3 py-1 bg-blue-500/10 text-blue-400 text-[10px] font-black uppercase tracking-widest rounded-full border border-blue-500/20">
                      {moto.category}
                    </span>
                    <div className="flex gap-2">
                      <button
                        onClick={() => openForm(moto)}
                        className="p-2.5 bg-slate-800 hover:bg-slate-700 rounded-xl text-slate-400 hover:text-white transition-all scale-0 group-hover:scale-100 duration-300"
                        title="Editar"
                      >
                        <Edit3 size={16} />
                      </button>
                      <button
                        onClick={() => handleDelete(moto.id)}
                        className="p-2.5 bg-red-500/5 hover:bg-red-500/20 rounded-xl text-red-500/50 hover:text-red-500 transition-all scale-0 group-hover:scale-100 duration-300 delay-75"
                        title="Remover"
                      >
                        <Trash2 size={16} />
                      </button>
                    </div>
                  </div>

                  <div className="mb-8">
                    <h3 className="text-2xl font-black mb-1 group-hover:text-blue-400 transition-colors flex items-center gap-2">
                      {moto.model}
                      <ArrowUpRight size={18} className="opacity-0 group-hover:opacity-100 translate-x-[-10px] group-hover:translate-x-0 transition-all text-blue-400" />
                    </h3>
                    <p className="text-slate-500 font-bold uppercase text-xs tracking-widest">{moto.brand}</p>
                  </div>

                  <div className="grid grid-cols-2 gap-4 mb-8">
                    <div className="flex items-center gap-2 text-slate-400">
                      <Calendar size={14} className="text-slate-600" />
                      <span className="text-sm font-medium">{moto.year}</span>
                    </div>
                    <div className="flex items-center gap-2 text-slate-400">
                      <Gauge size={14} className="text-slate-600" />
                      <span className="text-sm font-medium">{moto.displacement} cc</span>
                    </div>
                  </div>

                  <div className="flex justify-between items-center pt-6 border-t border-slate-800 group-hover:border-blue-500/20 transition-colors">
                    <div className="flex items-center gap-1">
                      <span className="text-blue-500/50 font-bold text-lg">R$</span>
                      <span className="text-3xl font-black bg-clip-text text-transparent bg-gradient-to-r from-white to-slate-400">
                        {moto.price.toLocaleString('pt-BR', { minimumFractionDigits: 0 })}
                      </span>
                    </div>
                    <div className={`w-3 h-3 rounded-full shadow-[0_0_10px_rgba(59,130,246,0.5)]`} style={{ backgroundColor: moto.color.toLowerCase() }}></div>
                  </div>
                </div>
              ))
            )}
          </div>
        )}
      </div>

      {/* Modal / Overlay Form */}
      {isModalOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-6 animate-in fade-in duration-300">
          <div className="absolute inset-0 bg-slate-950/80 backdrop-blur-md" onClick={() => setIsModalOpen(false)}></div>

          <div className="relative bg-[#0f172a] border border-slate-800 w-full max-w-xl rounded-[2.5rem] p-10 shadow-2xl overflow-y-auto max-h-[90vh]">
            <div className="flex justify-between items-center mb-8">
              <div>
                <h2 className="text-3xl font-black">{editingMoto ? 'EDITAR' : 'NOVA'} MOTOCICLETA</h2>
                <p className="text-slate-500 text-xs font-bold uppercase tracking-widest">Preencha os dados técnicos</p>
              </div>
              <button
                onClick={() => setIsModalOpen(false)}
                className="p-3 bg-slate-800 hover:bg-slate-700 text-slate-400 hover:text-white rounded-2xl transition-all"
              >
                <X size={24} />
              </button>
            </div>

            <form onSubmit={handleSubmit} className="space-y-6">
              <div className="group">
                <label className="flex items-center gap-2 text-[10px] font-black text-slate-500 uppercase tracking-widest mb-2 px-1">
                  ID de Identificação
                </label>
                <input
                  type="text" name="id" value={formData.id} onChange={handleInputChange}
                  disabled={!!editingMoto}
                  className="w-full bg-slate-950 border border-slate-800 rounded-2xl py-3 px-5 focus:border-blue-500 transition-all outline-none disabled:opacity-30"
                  required
                />
              </div>

              <div className="grid grid-cols-2 gap-6">
                <div>
                  <label className="text-[10px] font-black text-slate-500 uppercase tracking-widest mb-2 px-1 block">Marca</label>
                  <input
                    type="text" name="brand" value={formData.brand} onChange={handleInputChange}
                    className="w-full bg-slate-950 border border-slate-800 rounded-2xl py-3 px-5 focus:border-blue-500 transition-all outline-none"
                    required
                  />
                </div>
                <div>
                  <label className="text-[10px] font-black text-slate-500 uppercase tracking-widest mb-2 px-1 block">Modelo</label>
                  <input
                    type="text" name="model" value={formData.model} onChange={handleInputChange}
                    className="w-full bg-slate-950 border border-slate-800 rounded-2xl py-3 px-5 focus:border-blue-500 transition-all outline-none"
                    required
                  />
                </div>
              </div>

              <div className="grid grid-cols-2 gap-6">
                <div>
                  <label className="text-[10px] font-black text-slate-500 uppercase tracking-widest mb-2 px-1 block">Ano de Fabricação</label>
                  <input
                    type="number" name="year" value={formData.year} onChange={handleInputChange}
                    className="w-full bg-slate-950 border border-slate-800 rounded-2xl py-3 px-5 focus:border-blue-500 transition-all outline-none"
                    required
                  />
                </div>
                <div>
                  <label className="text-[10px] font-black text-slate-500 uppercase tracking-widest mb-2 px-1 block">Cilindrada (CC)</label>
                  <input
                    type="number" name="displacement" value={formData.displacement} onChange={handleInputChange}
                    className="w-full bg-slate-950 border border-slate-800 rounded-2xl py-3 px-5 focus:border-blue-500 transition-all outline-none"
                    required
                  />
                </div>
              </div>

              <div className="grid grid-cols-2 gap-6">
                <div>
                  <label className="text-[10px] font-black text-slate-500 uppercase tracking-widest mb-2 px-1 block">Cor Principal</label>
                  <input
                    type="text" name="color" value={formData.color} onChange={handleInputChange}
                    className="w-full bg-slate-950 border border-slate-800 rounded-2xl py-3 px-5 focus:border-blue-500 transition-all outline-none"
                    required
                  />
                </div>
                <div>
                  <label className="text-[10px] font-black text-slate-500 uppercase tracking-widest mb-2 px-1 block">Categoria Estilo</label>
                  <input
                    type="text" name="category" value={formData.category} onChange={handleInputChange}
                    placeholder="Ex: Naked, Sport"
                    className="w-full bg-slate-950 border border-slate-800 rounded-2xl py-3 px-5 focus:border-blue-500 transition-all outline-none"
                    required
                  />
                </div>
              </div>

              <div>
                <label className="flex items-center gap-2 text-[10px] font-black text-slate-500 uppercase tracking-widest mb-2 px-1">
                  Preço do Veículo (R$)
                </label>
                <div className="relative">
                  <div className="absolute left-5 top-1/2 -translate-y-1/2 text-blue-500 font-bold">R$</div>
                  <input
                    type="number" step="0.01" name="price" value={formData.price} onChange={handleInputChange}
                    className="w-full bg-slate-950 border border-slate-800 rounded-2xl py-4 pl-12 pr-6 focus:border-blue-500 transition-all outline-none text-2xl font-black text-blue-400"
                    required
                  />
                </div>
              </div>

              <button
                type="submit"
                className="w-full flex items-center justify-center gap-3 bg-gradient-to-r from-blue-600 to-indigo-600 hover:from-blue-500 hover:to-indigo-500 active:scale-95 transition-all text-white font-black py-5 mt-8 rounded-2xl text-xl shadow-xl shadow-blue-500/20 uppercase tracking-widest"
              >
                <Save size={24} />
                {editingMoto ? 'Finalizar Edição' : 'Publicar Inventário'}
              </button>
            </form>
          </div>
        </div>
      )}
    </div>
  )
}

export default App
