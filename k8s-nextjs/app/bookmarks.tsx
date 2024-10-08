'use client';

import { useState, useEffect } from 'react';
import { getBookmarks, createBookmark, deleteBookmark, updateBookmark } from './services/bookmarkService'
import Modal from './modal';

interface Bookmark {
  id: string;
  description: string;
  url: string;
  createdAt: string;
}

const Bookmarks: React.FC = () => {
  const [bookmarks, setBookmarks] = useState<Bookmark[]>([]);
  const [page, setPage] = useState<number>(1);
  const [query, setQuery] = useState<string>('');
  const [loading, setLoading] = useState<boolean>(true);
  const [description, setDescription] = useState<string>('');
  const [url, setUrl] = useState<string>('');
  const [formError, setFormError] = useState<string>('');


  const [editingBookmarkId, setEditingBookmarkId] = useState<string | null>(null);
  const [editDescription, setEditDescription] = useState<string>('');
  const [editUrl, setEditUrl] = useState<string>('');
  const [isModalOpen, setIsModalOpen] = useState(false);

  const fetchBookmarks = async () => {
    setLoading(true);
    try {
      const data = await getBookmarks(page, query);
      setBookmarks(data);
    } catch (error) {
      console.error('Failed to fetch bookmarks' + error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchBookmarks();
  }, [page, query]);

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    setPage(1); // Reset to page 1 when searching
  };

  const handleCreateBookmark = async (e: React.FormEvent) => {
    e.preventDefault();
    setFormError(''); // Reset error message

    if (!description || !url) {
      setFormError('Description and URL are required.');
      return;
    }

    try {
      const newBookmark = await createBookmark({ description, url });
      console.log(newBookmark);
      // Clear input fields after successful creation
      setDescription('');
      setUrl('');
      fetchBookmarks();
    } catch (error) {
      console.error('Error creating bookmark', error);
      setFormError('Failed to create bookmark. Please try again.');
    }
  };

  const handleDeleteBookmark = async (id: string) => {
    if (confirm('Are you sure you want to delete this bookmark?')) {
      try {
        await deleteBookmark(id);
        fetchBookmarks();
      } catch (error) {
        console.error('Error deleting bookmark', error);
        alert('Failed to delete bookmark. Please try again.');
      }
    }
  };

  const handleEdit = (bookmark: Bookmark) => {
    setEditingBookmarkId(bookmark.id);
    setEditDescription(bookmark.description);
    setEditUrl(bookmark.url);
    setIsModalOpen(true); // Open the modal when editing
  };

  // Handle updating a bookmark
  const handleUpdateBookmark = async (id: string) => {
    if (!editDescription || !editUrl) {
      alert('Description and URL are required for update bookmark.');
      return;
    }

    try {
      const updatedBookmark = await updateBookmark(id, { description: editDescription, url: editUrl });
      console.log(updatedBookmark);
      setEditingBookmarkId(null);
      setIsModalOpen(false); // Close the editing form
      fetchBookmarks();
    } catch (error) {
      console.error('Error updating bookmark', error);
      alert('Failed to update bookmark. Please try again.');
    }
  };

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Bookmarks</h1>


      {/* Create Bookmark Form */}
      <div className="mb-8">
        <h2 className="text-2xl font-bold mb-4">Add a New Bookmark</h2>
        <form onSubmit={handleCreateBookmark} className="flex flex-col space-y-4">
          <input
            type="text"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            placeholder="Description"
            className="border p-2 rounded"
          />
          <input
            type="text"
            value={url}
            onChange={(e) => setUrl(e.target.value)}
            placeholder="URL"
            className="border p-2 rounded"
          />
          {formError && <p className="text-red-500">{formError}</p>}
          <button
            type="submit"
            className="bg-green-500 text-white p-2 rounded hover:bg-green-600 transition"
          >
            Add Bookmark
          </button>
        </form>
      </div>

      <form onSubmit={handleSearch} className="mb-4">
        <input
          type="text"
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          placeholder="Search bookmarks..."
          className="border p-2 mr-2 rounded"
        />
      </form>

      {loading ? (
        <p className="text-center">Loading bookmarks...</p>
      ) : bookmarks.length === 0 ? (
        <p className="text-center">No bookmarks found.</p>
      ) : (
        <div className="overflow-x-auto">
          {/* Table for displaying bookmarks */}
          <table className="min-w-full bg-white shadow-md rounded-lg overflow-hidden">
            <thead className="bg-blue-500 text-white">
              <tr>
                <th className="py-3 px-4 text-left">ID</th>
                <th className="py-3 px-4 text-left">Title</th>
                <th className="py-3 px-4 text-left">URL</th>
                <th className="py-3 px-4 text-left">Created At</th>
                <th className="py-3 px-4 text-left">Actions</th>
              </tr>
            </thead>
            <tbody>
              {bookmarks.map((bookmark) => (
                <tr key={bookmark.id} className="border-b hover:bg-gray-50 transition">
                  <td className="py-3 px-4">{bookmark.id}</td>
                  <td className="py-3 px-4 font-medium">{bookmark.description}</td>
                  <td className="py-3 px-4">
                    <a href={bookmark.url} target="_blank" rel="noopener noreferrer" className="text-blue-600 underline">
                      {bookmark.url}
                    </a>
                  </td>
                  <td className="py-3 px-4">{new Date(bookmark.createdAt).toLocaleDateString()}</td>
                  <td className="py-3 px-4 flex space-x-2">

                    {/* Edit Button */}
                    <button
                      onClick={() => handleEdit(bookmark)}
                      className="bg-yellow-500 text-white px-3 py-1 rounded hover:bg-yellow-600 transition"
                    >
                      Edit
                    </button>

                    <button
                      onClick={() => handleDeleteBookmark(bookmark.id)}
                      className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600 transition"
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {/* Modal for updating the bookmark */}
      <Modal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)}>
        <h2 className="text-2xl font-bold mb-4">Update Bookmark</h2>
        <input
          type="text"
          value={editDescription}
          onChange={(e) => setEditDescription(e.target.value)}
          placeholder="Description"
          className="border p-2 rounded mb-2 w-full"
        />
        <input
          type="text"
          value={editUrl}
          onChange={(e) => setEditUrl(e.target.value)}
          placeholder="URL"
          className="border p-2 rounded mb-2 w-full"
        />
        <button
          onClick={() => handleUpdateBookmark(editingBookmarkId!)}
          className="bg-green-500 text-white px-3 py-2 rounded hover:bg-green-600 transition w-full"
        >
          Save Changes
        </button>
      </Modal>

      {/* Pagination */}
      <div className="mt-6 flex justify-center">
        <button
          onClick={() => setPage((prev) => Math.max(prev - 1, 1))}
          disabled={page === 1}
          className="bg-gray-200 p-2 rounded mr-2 hover:bg-gray-300 transition disabled:opacity-50"
        >
          Previous
        </button>
        <button
          onClick={() => setPage((prev) => prev + 1)}
          className="bg-gray-200 p-2 rounded hover:bg-gray-300 transition"
        >
          Next
        </button>
      </div>
    </div>
  );
};

export default Bookmarks;
